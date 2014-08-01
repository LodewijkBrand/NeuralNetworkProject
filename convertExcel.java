
import java.io.*;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author mhd57_000
 */
public class convertExcel {

    private ArrayList<Input> inputs;
    private ArrayList<Output> outputs;
    private int NUM_OUT;
    private int NUM_IN;
    private final String origFile,newFile;
	
    public static void main (String[] args){
    	convertExcel test = new convertExcel("C:\\Users\\mhd57_000\\Documents\\NetBeansProjects\\BBB_NeuralNet\\src\\BBBdataset3.txt");
    }
	
	/**
	 * Constructor begins the parsing process
	 */
    public convertExcel(String orig){
	origFile = orig;
        newFile = "bbbDataFixed.txt";
	parse();
        writeNewFile();
    }
	
    /**
     * Parses the CSV and stores it in Input and Output objects
    */
    public void parse(){
	inputs = new ArrayList<Input>();
	outputs = new ArrayList<Output>();
        boolean initial = true;
	BufferedReader br = null;
	String line;
	String delimiter = "\t";
        int counter = 1;
	try{
            br = new BufferedReader(new FileReader(origFile));			
            while ((line = br.readLine()) != null){
                String[] individualData = line.split(delimiter);
                addIndividual(individualData);
                if (initial){
                    NUM_OUT = 1;
                    NUM_IN = individualData.length - 1;
                    initial = false;
                }
                if (individualData.length != (NUM_OUT + NUM_IN)){ 
                    throw new IllegalArgumentException("The Data File is not Uniform at line " + counter);
                }
                counter++;    
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
    }

    /**
     * Helper method that parses an array of data and stores it in an Input
     */
    private void addIndividual(String[] data){
	ArrayList<Double> currentOutputs = new ArrayList<Double>();
        
        System.out.println(data[0] + '\t' + data[1]);
	currentOutputs.add(Double.parseDouble(data[0]));
	Output newOutput = new Output(currentOutputs);
		
	outputs.add(newOutput);
		
	ArrayList<Double> currentInputs = new ArrayList<Double>();
	for (int i = 1; i < data.length; i++){
            currentInputs.add(Double.parseDouble(data[i]));
	}
	
        Input newInput = new Input(currentInputs);

	inputs.add(newInput);
    }
    
    public void writeNewFile(){
        String delimiter = "\t";
        String newline = "\n";
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(newFile));
            for (int i = 0; i < inputs.size(); i++) {
                System.out.println(NUM_IN);
                System.out.println(NUM_OUT);
                for (int j = 0; j < NUM_IN; j++){
                    //System.out.println(inputs.get(i).getInputs().get(j));
                    if (j != NUM_IN - 1){
                        out.write(inputs.get(i).getInputs().get(j).toString() + delimiter);
                    }
                    else {out.write(inputs.get(i).getInputs().get(j).toString());}
                }
                out.newLine();
                for (int k = 0; k < NUM_OUT; k++){
                    if (k != NUM_OUT - 1){
                        out.write(outputs.get(i).getOutputs().get(k).toString() + delimiter);
                    }
                    else {out.write(outputs.get(i).getOutputs().get(k).toString());}
                }
                out.newLine();
            }
            out.close();
        } catch (IOException e) {}
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
}
