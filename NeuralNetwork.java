/**
 * This object represents the entire Neural Network. This is where most of the algorithm takes place. 
 * @author Lou Brand
 * @author Matthew Dickinson
 */

import java.util.ArrayList;

public class NeuralNetwork implements java.io.Serializable{
    private InputLayer myInput;
    private HiddenLayer myHidden;
    private OutputLayer myOutput;
    private boolean GA;
    private final double LEARNING_RATE;

    public NeuralNetwork(double learnRate, boolean isGA){
        LEARNING_RATE = learnRate;
        GA = isGA;
    }

    /**
    * Adds a new Input and Output object into the NeuralNetwork for training and testing purposes
    * Remember: This keeps the integrity of the edges!
    * @param newInput The next Input
    * @param newOutput The next Output
    */
    public void newIO(IOTuple newIO){
        Input newInput = newIO.getInput();
        Output newOutput = newIO.getOutput();

        ArrayList<Neuron> inputNeurons = myInput.getNeurons();
        for (int i = 0; i < inputNeurons.size()-1; i++){		// The minus-one is to not change the BIAS!!!!!!!!
            Neuron current = inputNeurons.get(i);
            double newValue = newInput.getInputs().get(i);
            current.setValue(newValue);
        }
        myOutput.setExpectedOutputs(newOutput);
    }

    /**
    * Initializes the NeuralNetwork's Synapses between each Layer
    */
    public void initialize(IOTuple initialIO){
        Input initialInput = initialIO.getInput();
        Output initialOutput = initialIO.getOutput();

        myInput = new InputLayer(initialInput);
        myHidden = new HiddenLayer((int)(initialInput.size() * .5));
        myOutput = new OutputLayer(initialOutput);

        for (Neuron inputNeuron : myInput.getNeurons()){
            for (Neuron hiddenNeuron : myHidden.getNeurons()){
                if (!hiddenNeuron.isBiased()){
                    Synapse inputToHidden = new Synapse(inputNeuron, hiddenNeuron);
                    }
                }
            }
        for (Neuron hiddenNeuron : myHidden.getNeurons()){
            for (Neuron outputNeuron : myOutput.getNeurons()){
                Synapse hiddenToOutput = new Synapse(hiddenNeuron, outputNeuron);
            }
        }
    }
    
    public void initializeSample(Input sample){
        ArrayList<Double> inputValues = sample.getInputs();
        ArrayList<Neuron> inputNeurons = myInput.getNeurons();
        for (int i = 0; i < inputNeurons.size() - 1; i++){
            inputNeurons.get(i).setValue(inputValues.get(i));
        }
        
    }

    /**
    * Feeds the InputLayer through the NeuralNetwork and calculates the OutputLayer
    */
    public void feedForward(){
        for (Neuron hidden : myHidden.getNeurons()){
            ArrayList<Synapse> edges = hidden.getInputSynapses();
            double currentSum = 0.0;
            for (Synapse current : edges){
                currentSum += current.getWeight() * current.getOrigin().getValue();
            }
            hidden.setValue(sigmoid(currentSum));
        }
        for (Neuron output : myOutput.getNeurons()){
            ArrayList<Synapse> edges = output.getInputSynapses();
            double currentSum = 0.0;
            for (Synapse current : edges){
                currentSum += current.getWeight() * current.getOrigin().getValue();
            }
            output.setValue(sigmoid(currentSum));
        }
    }

    /**
    * The back-propagation algorithm. This is how the NeuralNetwork learns!
    * x = Input Value
    * w1 = Synapse #1
    * w2 = Synapse #2
    * d = Desired Output
    * z = Calculated Output (via Neural Net)
    * P = Performance Function = -1/2 * (d - z) ^ 2
    * We want to maximize P. By changing the weights w1 and w2.
    * Follow below:
    * Change in w* = learningRate * dP/dw*
    * Schematic: x * w1 = p1 --> sigmoid(sum) = y * w2 = p2 --> sigmoid(sum) = z --> -1/2 * (d - z) ^ 2 = P
    * Then: dP/dw2 = dP/dz * dz/dw2 = (d - z) * dz/dp2 * dp2/dw2 = (d - z) * dz/dp2 * y
    * And : dP/dw1 = dP/dz * dp/dw1 = (d - z) * dz/dp2 * dp2/dw1 = (d - z) * dz/dp2 * dp2/dy * dy/dw1 = (d - z) * dz/dp2 * w2 * dy/dp1 * x
    * At the end: dP/dw2 = (d - z) * z * (1 - z) * y
    * 			   dP/dw1 = (d - z) * z * (1 - z) * w2 * y * (1 - y) * x
    */
    public void backProp(){
        for (int i = 0; i < myOutput.size(); i++){
            Neuron outputNeuron = myOutput.get(i);
            double z = outputNeuron.getValue();			//Calculated Output
            double d = myOutput.getExpectedOutput(i);	//Desired Output
            for (Synapse w2Synapse : outputNeuron.getInputSynapses()){
                double w2 = w2Synapse.getWeight();	//Second tier Synapse weight
                double y = w2Synapse.getOrigin().getValue();	//Hidden Neuron value
                double dPdw2 = (d - z) * z * (1.0 - z) * y;
                for (Synapse w1Synapse : w2Synapse.getOrigin().getInputSynapses()){
                    double w1 = w1Synapse.getWeight();			//First tier Synapse weight
                    double x = w1Synapse.getOrigin().getValue();	//Input Neuron value
                    double dPdw1 = (d - z) * z * (1.0 - z) * y * w2 * (1.0 - y) * x;
                    double newvalue = w1 + dPdw1*LEARNING_RATE;
                    w1Synapse.setWeight(w1 + dPdw1 * LEARNING_RATE);
                }
                w2Synapse.setWeight(w2 + dPdw2 * LEARNING_RATE);
            }
        }
    }

    /**
    * The mathematically "convenient" sigmoid function. 
    * @return Self explanitory
    */
    private double sigmoid(double sum){
        return 1.0 / (1.0 + Math.exp(-sum));
    }

    /**
    * Gets the expected output of this NeuralNetwork given a specific Input and Output pair
    * @return The expected output
    */
    public double getExpectedOutput(){
        return myOutput.getExpectedOutput(0);
    }

    /**
    * Gets the calculated value of this NeuralNetwork after an Input has been fed-forward
    */
    public double getCalculatedValue(){
        return myOutput.get(0).getValue();
    }
    
    public boolean getGA(){
        return GA;
    }
    
    public double getLR(){
        return LEARNING_RATE;
    }
}
