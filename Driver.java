/**
 * The main Driver method of the Neural Network
 * @author Lou Brand
 */

import java.util.ArrayList;
import java.io.*;

public class Driver{
	public static void main (String[] args){
        ArrayList<IOTuple> inputOutputTuples;
        
        try{
            InputStream file = new FileInputStream("myData.ser");
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream (buffer);
            inputOutputTuples = (ArrayList<IOTuple>)input.readObject();

            int EPOCHS = 500;
            
            KFoldTest myTest = new KFoldTest(inputOutputTuples, EPOCHS);
        }
        catch(ClassNotFoundException ex){System.out.println("The class wasn't found!");}
        catch(IOException ex){System.out.println("There was an error!");}
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
