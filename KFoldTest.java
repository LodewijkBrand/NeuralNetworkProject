import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class KFoldTest{
    private ArrayList<IOTuple> myData;
    private int epochs;
    private final double CONFIDENCE = .9; //95% accuracy
    private final int NUM_FOLDS = 10;
    
    public KFoldTest(ArrayList<IOTuple> data, int numEpochs){
        epochs = numEpochs;
        myData = data; 
        Collections.shuffle(myData);
        kFold();
    }
    
    private void kFold(){
        int numInTestSet = myData.size()/NUM_FOLDS;
        
        for (int i = 0; i < NUM_FOLDS; i++){
            NeuralNetwork currentNN = new NeuralNetwork();
            
            currentNN.initialize(myData.get(0)); //Initialize the NeuralNetwork with a piece of data. (This will be changed later)
            
            List<IOTuple> leftTrainSet = myData.subList(0, i*numInTestSet);
            List<IOTuple> testSet = myData.subList(i*numInTestSet, (i+1)*numInTestSet);
            List<IOTuple> rightTrainSet = myData.subList((i+1)*numInTestSet, myData.size());
            
            ArrayList<IOTuple> trainingSet = new ArrayList<IOTuple>();
            trainingSet.addAll(leftTrainSet);
            trainingSet.addAll(rightTrainSet);
            
            for (int j = 0; j < epochs; j++){
                for (IOTuple trainingIO : trainingSet){
                    currentNN.newIO(trainingIO);
                    currentNN.feedForward();
                    currentNN.backProp();
                }
            }
            
            double sumSquaredError = 0.0;
            int numRight = 0; 
            
            for (IOTuple testingIO : testSet){
                currentNN.newIO(testingIO);
                currentNN.feedForward();
                double calculated = currentNN.getCalculatedValue(); 
                double expected = currentNN.getExpectedOutput();
                //System.out.println("Expected: " + expected + " Calculated: " + calculated);
                
                double error = calculated - expected;
                System.out.println(error);
                sumSquaredError += Math.pow(error, 2);
                if (Math.abs(error) < CONFIDENCE){
                    numRight++;
                }
            }
            
            System.out.println("Fold " + (i+1) + ", has a mean SSE of: " + sumSquaredError/(double)testSet.size());
            System.out.println("The NeuralNetwork had a 95% confidence level " + (100.0 * (double)numRight/(double)testSet.size()) + "% of the time.");
        }
    }
}
