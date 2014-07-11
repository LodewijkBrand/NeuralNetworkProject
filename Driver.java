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

public class Driver{
    KFoldTest myTest;
    int EPOCHS;
    
    // delim holds either Tab or Comma, and GA will be used to determine type of NN
    public Driver (String fileName, String delim, boolean GA){
        ArrayList<IOTuple> inputOutputTuples;
        String delimiter;
        
        if (delim == "Tab"){
            delimiter = "\t";
        }
        else {
            delimiter = ",";
        }
        
        Parser test = new Parser(fileName, delimiter);
        
        try{
            InputStream file = new FileInputStream("myData.ser");
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream (buffer);
            inputOutputTuples = (ArrayList<IOTuple>)input.readObject();

            EPOCHS = 500;
            
            myTest = new KFoldTest(inputOutputTuples, EPOCHS, GA);
        }
        catch(ClassNotFoundException ex){System.out.println("The class wasn't found!");}
        catch(IOException ex){System.out.println("There was an error!");}
    }
    
    public KFoldTest getKFoldTest(){
        return myTest;
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
