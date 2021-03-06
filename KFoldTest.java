/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Lou Brand & Matthew Dickinson
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class KFoldTest implements java.io.Serializable{
    private ArrayList<IOTuple> myData;
    private int epochs;
    private double avgConfLevel;
    private double bestConf;
    private NeuralNetwork bestNet;    
    private boolean GA;
    private final double CONFIDENCE = .95; //95% accuracy
    private final int NUM_FOLDS = 2; 
    private final double learnRate;

    
    // GA will be used to select the genetic algorithm or normal NN
    public KFoldTest(ArrayList<IOTuple> data, int numEpochs, boolean isGA, double LR){
        epochs = numEpochs;
        myData = data; 
        learnRate = LR;
        Collections.shuffle(myData);
        GA = isGA;
        kFold();
    }
    
    private void kFold(){
        int numInTestSet = (int)(myData.size()/NUM_FOLDS);
        double confLevel;
        
        bestConf = 0.0;
        
        for (int i = 0; i < NUM_FOLDS; i++){
            NeuralNetwork currentNN = new NeuralNetwork(learnRate,GA);
            
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
                //System.out.println(error);
                sumSquaredError += Math.pow(error, 2);
                if (Math.abs(error) < CONFIDENCE){
                    numRight++;
                }
            }
            
        //  System.out.println("Fold " + (i+1) + ", has a mean SSE of: " + sumSquaredError/(double)testSet.size());
            confLevel = (100.0 * (double)numRight/(double)testSet.size());
            avgConfLevel += confLevel;
            if (confLevel > bestConf){
                bestConf = confLevel;
                bestNet = currentNN;
            }
            System.out.println("The NeuralNetwork had a 95% confidence level " + confLevel + "% of the time.");
        }
        avgConfLevel = avgConfLevel/NUM_FOLDS;
    }
    public double getAvgConfLevel(){
        return avgConfLevel;
    }
    
    public double getBestConfLevel(){
        return bestConf;
    }
    
    public NeuralNetwork getBestNN(){
        return bestNet;
    }
    
}
