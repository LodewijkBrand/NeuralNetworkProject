import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class KFoldTest{
    private ArrayList<IOTuple> myData;
    private int epochs;
    private final double CONFIDENCE = .05; //95% accuracy
    private final int NUM_FOLDS = 10;
    private int numInTestSet;
    
    public KFoldTest(ArrayList<IOTuple> data, int numEpochs){
        epochs = numEpochs;
        myData = data; 
        numInTestSet = myData.size()/NUM_FOLDS;
        
        Collections.shuffle(myData);
        //kFoldNeural();
        kFoldGenetic();
    }
    
    private void kFoldGenetic(){
        int popSize = 10;
        
        for (int i = 0; i < NUM_FOLDS; i++){
            List<IOTuple> leftTrainSet = myData.subList(0, i*numInTestSet);
            List<IOTuple> testSet = myData.subList(i*numInTestSet, (i+1)*numInTestSet);
            List<IOTuple> rightTrainSet = myData.subList((i+1)*numInTestSet, myData.size());
            
            ArrayList<IOTuple> trainingSet = new ArrayList<IOTuple>();
            trainingSet.addAll(leftTrainSet);
            trainingSet.addAll(rightTrainSet);
            
            FitnessFunction myFF = new FitnessFunction("min", trainingSet);
            
            Population currentPop = new Population(popSize, trainingSet, myFF);
            
            for (int j = 0; j < 50; j++){
                currentPop.evolve();
            }
            
            double sumSquaredError = 0.0;
            int numRight = 0; 
            NeuralNetwork currentNN = currentPop.getIndivs().get(0);
            
            for (IOTuple testingIO : testSet){
                currentNN.newIO(testingIO);
                currentNN.feedForward();
                double calculated = currentNN.getCalculatedValue(); 
                double expected = currentNN.getExpectedOutput();
                System.out.println("Expected: " + expected + " Calculated: " + calculated);
                
                double error = calculated - expected;
                //System.out.println(error);
                sumSquaredError += Math.pow(error, 2);
                if (Math.abs(error) < CONFIDENCE){
                    numRight++;
                }
            }
            
            System.out.println("Fold " + (i+1) + ", has a mean SSE of: " + sumSquaredError/(double)testSet.size());
            System.out.println("The NeuralNetwork had a 95% confidence level " + (100.0 * (double)numRight/(double)testSet.size()) + "% of the time.");
        }
    }
    
    private void kFoldNeural(){
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
                System.out.println("Expected: " + expected + " Calculated: " + calculated);
                
                double error = calculated - expected;
                //System.out.println(error);
                sumSquaredError += Math.pow(error, 2);
                if (Math.abs(error) < CONFIDENCE){
                    numRight++;
                }
            }
            
            /*for (Synapse edge : currentNN.getEdges()){
                System.out.print(edge.getWeight() + "\t");
            }
            System.out.println();*/
            
            
            System.out.println("Fold " + (i+1) + ", has a mean SSE of: " + sumSquaredError/(double)testSet.size());
            System.out.println("The NeuralNetwork had a 95% confidence level " + (100.0 * (double)numRight/(double)testSet.size()) + "% of the time.");
        }
    }
}
