/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * The main Driver method of the Neural Network
 * @author Lou Brand & Matthew Dickinson
 */

import java.util.ArrayList;
import java.io.*;

public class Driver implements java.io.Serializable{
    private KFoldTest myTest;
    private int EPOCHS;
    private boolean isGA;
    private String fileName;
    private String delimiter;
    private Parser myParser;
    private double sampleOutput;
    // delim holds either Tab or Comma, and GA will be used to determine type of NN
    public Driver (String file, String delim, boolean GA){
        
        isGA = GA;
        fileName = file;
        
        // Set delimiter
        if (delim == "Tab"){delimiter = "\t";}
        else {delimiter = ",";}
        
        myParser = new Parser(fileName, delimiter);
    }
    
    // Driver will then pass information to KFoldTest and train the network
    public void trainNetwork(double LR){

        ArrayList<IOTuple> inputOutputTuples;
        try{
            InputStream file = new FileInputStream("myData.ser");
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream (buffer);
            inputOutputTuples = (ArrayList<IOTuple>)input.readObject();

            EPOCHS = 500;
            
            myTest = new KFoldTest(inputOutputTuples, EPOCHS, isGA, LR);
        }
        catch(ClassNotFoundException ex){System.out.println("The class wasn't found!");}
        catch(IOException ex){System.out.println("There was an error!");}
    }
    
    // Driver will read and evaluate a new set of inputs with the network
    public void testSample(String testFile, String delimit){
        // Set delimit
        if (delimit == "Tab"){delimit = "\t";}
        else {delimit = ",";}
        // Retrieve the best network from KFoldTest
        NeuralNetwork bestNN = myTest.getBestNN();
        
        // Parse data file of the sample (No output, one input)
        myParser.parseCheck(testFile, delimit);
        
        // Test this data with the stored network
        bestNN.initializeSample(myParser.getSampleInput());
        bestNN.feedForward();
        
        // Save the output from this sample
        sampleOutput = bestNN.getCalculatedValue();
    }
    
    public KFoldTest getKFoldTest(){
        return myTest;
    }
    
    public double getSampleOutput(){
        return sampleOutput;
    }
}

//Testing Materials

/*IOTuple initialTuple = inputOutputTuples.get(0);

NeuralNetwork myNeuralNetwork = new NeuralNetwork();
myNeuralNetwork.initialize(initialTuple);

for (int k = 0; k < EPOCHS; k++){
    for (int i = 0; i < inputOutputTuples.size() - 20; i++){
        myNeuralNetwork.newIO(inputOutputTuples.get(i));
        myNeuralNetwork.feedForward();
        myNeuralNetwork.backProp();
    }
}

for (int j = inputOutputTuples.size()-20; j < inputOutputTuples.size(); j++){
    myNeuralNetwork.newIO(inputOutputTuples.get(j));
    double expected = myNeuralNetwork.getExpectedOutput();
    myNeuralNetwork.feedForward();
    System.out.println("Calculated: " + myNeuralNetwork.getCalculatedValue() + "\t Expected: " + expected);
}*/
