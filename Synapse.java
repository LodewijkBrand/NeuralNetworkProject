/**
 * An object that represents a Synapse. A Synapse is used to connect two Neurons together.
 * @author Lou Brand
 */

import java.util.Random;

public class Synapse{
    private double weight;
    private Neuron origin, destination;
    private final int NUM_GENES = 64;

    /**
    * When a Synapse object is created it needs two Neuron objects it is connecting. 
    * @param from The Neuron this edge is coming from
    * @param to The Neuron this edge is going to
    */
    public Synapse(Neuron from, Neuron to){
        origin = from;
        destination = to;
        from.addOutput(this);
        to.addInput(this);
        setRandomWeight();
    }

    /**
    * Gives this Synapse a random value between [-1, 1]
    */
    private void setRandomWeight(){
        Random rand = new Random();
        weight = rand.nextDouble() * 30.0 - 15.0;
    }

    /**
    * Gives this Synapse a new weight. NOTE: Should the value of the weight stay between -1 and 1?
    * @param newWeight The new weight for this Synapse
    */
    public void setWeight(double newWeight){
        if (weight <= 15.0 && weight >= -15){
            weight = newWeight;
        }
    }
    
    /**
	 * Overloaded method to set this Synapse to a new value given a bit-string
	 * @param newValue Bit-string representation of a double
	 */
/*	public void setWeight(String newValue){
		double converted = parseGenome(newValue);
		setWeight(converted);
	}

    /**
    * Gets the weight of this Synapse
    */
    public double getWeight(){
        return weight;
    }

    /**
    * Gets the Neuron where this Synapse originates from
    * @return origin The Neuron this Synapse is coming from
    */
    public Neuron getOrigin(){
        return origin;
    }

    /**
    * Gets the Neuron where this Synapse is going to
    * @return destination The Neuron this Synapse is going to
    */
    public Neuron getDestination(){
        return destination;
    }
    
    /**
     * A two step mutation process for each Synapse
     */
    public void mutate(){
        double mutationProbability = .05;
        double maxPercentChange = .01;
        
        if (Math.random() < mutationProbability){
            double change = Math.random()*2.0 - 1;
            setWeight(weight + change * maxPercentChange * weight);
        }
        
 /*       String chromo = getBitString();
        mutationProbability = .001;
        char[] genes = chromo.toCharArray();
        
        for (int i = 0; i < chromo.length(); i++){
			if (Math.random() < mutationProbability){
				if (genes[i] == '1'){
					genes[i] = '0';
				} else {
					genes[i] = '1';
				}
			}
		}
		String mutation = String.valueOf(genes);
		
		setWeight(mutation);
        * */
    }
    
    public String getBitString(){
		String bitString = Long.toBinaryString(Double.doubleToRawLongBits(weight));
		while (bitString.length() < NUM_GENES){
			bitString = "0" + bitString;	//Normalize the bit-string just in case it is a positive value
		}
		return bitString;
    }
    
    /**
	 * Converts a bit-string to a double
	 */
	private double parseGenome(String genome){
		double value = Double.longBitsToDouble(Long.parseLong(genome.substring(1), 2));
		if (genome.charAt(0) == '0'){
			return value;
		} else {return -value;}
	}
}
