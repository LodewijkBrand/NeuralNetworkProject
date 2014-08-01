/**
 * An object that parses breastCancerData into individual Input and Output objects
 * @author Matthew Dickinson
 * @author Lou Brand
 */

import java.io.*;
import java.util.ArrayList;

public class Parser implements java.io.Serializable{
    private ArrayList<Input> inputs;
    private ArrayList<Output> outputs;
    private ArrayList<Double> maximaIn, minimaIn,maximaOut,minimaOut;
    private ArrayList<IOTuple> IOs;
    private Input sampleInput;
    private int NUM_OUT;
    private int NUM_IN;
    private final String csvFile;
    private final String delimiter;
/** For testing BCParser	    
    public static void main (String[] args){
    	BCParser test = new BCParser("breastCancerData.txt");
        for (int i = 0; i < 5; i++){
	        for (Double output : test.getOutputs().get(i).getOutputs()){
		        System.out.print(output + "\t");
            }
            for (Double input : test.getInputs().get(i).getInputs()){
				System.out.print(input + "\t");
			}
            System.out.println();
	    }
    }
*/    
	/**
	 * Constructor begins the parsing process
	 */
    public Parser(String csv, String delim){
        csvFile = csv;
        delimiter = delim;
        // When creating a new parser, automatically parse the data set and serialize the information
        parseTrain();
        createIOs();
        serializeIOs();
    }
	
    /**
     * Parses the data file and stores it in Input and Output objects
    */
    public void parseTrain(){
    	inputs = new ArrayList<>();
        outputs = new ArrayList<>();
        boolean initial = true;
	BufferedReader br = null;
	String lineIn;
        String lineOut;
        try{
            br = new BufferedReader(new FileReader(csvFile));
	
            while ((lineIn = br.readLine()) != null){
                String[] individualDataIn = lineIn.split(delimiter);
                // Conditional block to ensure # input lines = # output lines
                if ((lineOut = br.readLine()) == null){
                    throw new IllegalArgumentException("Different number of input and output lines");                                    
                }
                else {
                    String[] individualDataOut = lineOut.split(delimiter);
                    addIndividual(individualDataIn,individualDataOut);
                    // Set NUM IN and NUM_OUT with the first set of data
                    if (initial){
                        NUM_IN = individualDataIn.length;
                        NUM_OUT = individualDataOut.length;
                        initial = false;
                    }
                    // Throw an error if the data set is not uniform
                    if (individualDataIn.length != NUM_IN || individualDataOut.length != NUM_OUT){
                        throw new IllegalArgumentException("Dataset is not uniform!");
                    }
                }
            }
        } catch (FileNotFoundException e){e.printStackTrace();}
        catch (IOException e){e.printStackTrace();}
        finally{
            if (br != null){
                try{
                    br.close();
                } catch (IOException e) {e.printStackTrace();}
            }
        }
	getMinMax();	
	normalizeIndividuals();
    }
    
    // Similar to parseTrain, the main difference is that there is only one input line and no output lines
    // The input is not serialized (very small, not worth it)
    public void parseCheck(String testFile, String delimit){
        ArrayList<Double> input = new ArrayList<>();
	BufferedReader br = null;
	String lineIn;
        
        try{
            br = new BufferedReader(new FileReader(testFile));			
            if ((lineIn = br.readLine()) != null){
                String[] individualData = lineIn.split(delimit);
              
                // Conditional block to ensure # input lines = # output lines   
                if (individualData.length != NUM_IN){
                    System.out.println("NUM_IN: " + NUM_IN);
                    System.out.println("data length: " + individualData.length);
                    throw new IllegalArgumentException("Number of Inputs Does Not Match");
                }
                
                for (String dataIn1 : individualData) {
                    input.add(Double.parseDouble(dataIn1));
                }
                sampleInput = new Input(input);     
            }
        } catch (FileNotFoundException e){e.printStackTrace();}
        catch (IOException e){e.printStackTrace();}
        finally{
            if (br != null){
                try{
                    br.close();
                } catch (IOException e) {e.printStackTrace();}
            }
        }	
	normalizeSample();
    }
    
	
    /**
     * Helper method that parses an array of data and stores it in an Input
     */
    private void addIndividual(String[] dataIn, String[] dataOut){
        ArrayList<Double> currentOutputs = new ArrayList<>();
        ArrayList<Double> currentInputs = new ArrayList<>();
              
        for (String dataIn1 : dataIn) {
            currentInputs.add(Double.parseDouble(dataIn1));
        }
            
        Input newInput = new Input(currentInputs);
		
        for (String dataOut1 : dataOut){
            currentOutputs.add(Double.parseDouble(dataOut1));
        }
	
        Output newOutput = new Output(currentOutputs);
           
        inputs.add(newInput);
        outputs.add(newOutput);
	
    }

    
    /**
     * Normalizes all Input and Outputs to values between [0, 1]
     */
    private void normalizeIndividuals(){
        for (int i = 0; i < inputs.size(); i++){
            for (int j = 0; j < NUM_IN; j++){
                Input currentInput = inputs.get(i);
                currentInput.normalizeValue(minimaIn.get(j), maximaIn.get(j), j);
            }
            for (int k = 0; k < NUM_OUT; k++){
                Output currentOutput = outputs.get(i);
		        currentOutput.normalizeValue(minimaOut.get(k), maximaOut.get(k), k);
            }
	}
    }
    
    public void normalizeSample(){
        for (int j = 0; j < NUM_IN; j++){
            sampleInput.normalizeValue(minimaIn.get(j), maximaIn.get(j), j);
        }
    }
    
    /**
     * Finds the maximum and minimum of each Input and Output of the dataset
     */
    public void getMinMax(){
        /* 
         * Initialize all the minima and maxima arrays to the values held in
         * the first input and output objects. This eliminates the problem of
         * the first comparison (comparing to a null object or an object full
         * of zeroes)
         */
        maximaIn = new ArrayList<>(inputs.get(0).getInputs());
        maximaOut = new ArrayList<>(outputs.get(0).getOutputs());
        minimaIn = new ArrayList<>(inputs.get(0).getInputs());
        minimaOut = new ArrayList<>(outputs.get(0).getOutputs());
        
        double temp;
        
        // Loop through the inputs and outputs starting at the second position
        // If inputs.size() != outputs.size(), the program would have thrown an error in parse
        for(int i = 1; i < inputs.size();i++){
          
            // Check each input position
            for(int j = 0; j < NUM_IN; j++){
                // Temp holds the current input variable of the current input object being tested
                temp = inputs.get(i).getInputs().get(j);
                if(temp > maximaIn.get(j)){
                    maximaIn.set(j, temp);
                }
                else if (temp < minimaIn.get(j)){
                    minimaIn.set(j,temp);
                }
            }
            
            // Check each output position
            for(int k = 0; k < NUM_OUT; k++){
                // Temp holds the current output variable of the current output object being tested                
                temp = outputs.get(i).getOutputs().get(k);
                if(temp > maximaOut.get(k)){
                    maximaOut.set(k, temp);
                }
                else if (temp < minimaOut.get(k)){
                    minimaOut.set(k,temp);
                }
            }
        }
    }

    /**
    * Sets the IOTuple array
    */
    public void createIOs(){
	IOs = new ArrayList<>();
        for (int i = 0; i < inputs.size(); i++){
            IOTuple ioCurrent = new IOTuple(inputs.get(i), outputs.get(i));
            IOs.add(ioCurrent);
        }
    }
    
    /**
    * Serializes the IOTuple array
    */
    public void serializeIOs(){
     	try{
            String serial = "myData.ser";
            FileOutputStream fileOut = new FileOutputStream(serial);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(IOs);
            out.close();
            fileOut.close();
            System.out.println("Serialized data is saved in " + serial);
        }catch(IOException i){i.printStackTrace();}
    }   
    
    
    
    /**
    * Gets the list of Inputs to be used in the NeuralNetwork
    * @return inputs The Inputs for the NeuralNetwork
     */
    public ArrayList<Input> getInputs(){
		return inputs;
	}
	
    /**
     * Gets the list of Outputs to be used in the NeuralNetwork
     * @return outputs The Outputs for the NeuralNetwork
     */
    public ArrayList<Output> getOutputs(){
        return outputs;
    }
    
    /**
     * Gets the list of IOTuple pairs to be used in the NeuralNetwork
     * @return IOs The IOTuple pairs
     */
    public ArrayList<IOTuple> getIOTuples(){
        return IOs;
    }
    
    public Input getSampleInput(){
        return sampleInput;
    }
}
