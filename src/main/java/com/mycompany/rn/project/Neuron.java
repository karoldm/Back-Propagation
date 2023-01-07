package com.mycompany.rn.project;

import java.util.ArrayList;

/**
 *
 * @author karol
 */
public class Neuron {

    private ArrayList<Double> weights;
    private ArrayList<Double> inputs;
    private int function; //0 = logistic and 1 = hyperbolic tangent

    public Neuron(ArrayList<Double> weights, int function) {
        this.weights = weights;
        this.function = function;
    }

    public ArrayList<Double> getWeights() {
        return weights;
    }

    public void setWeights(ArrayList<Double> weights) {
        this.weights = weights;
    }

    public double getOutput() {
        //Calcular net
        double net = 0;

        for (int i = 0; i < inputs.size(); i++) {
            net += inputs.get(i) * weights.get(i);
        }
        //Aplicar função de transferência
        double output;
        if (function == 0) { //logistic
            output = 1 / (1 + Math.pow(Math.E, -net));
        } else { //hyperbolic tangent 
            output = (1 - Math.pow(Math.E, -2 * net)) / (1 + Math.pow(Math.E, -2 * net));
        }
        //Gerar output
        return output;
    }

    public void setInputs(ArrayList<Double> inputs) {
        this.inputs = inputs;
    }

}
