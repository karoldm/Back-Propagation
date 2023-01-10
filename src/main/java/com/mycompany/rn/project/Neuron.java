package com.mycompany.rn.project;

import java.util.ArrayList;

/**
 *
 * @author karol
 * Classe para representar um neuronio
 * Cada neuronio possui um array de pesos, as entradas correspondentes para cada peso
 * e uma função de transferencia 
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
        return this.weights;
    }

    public void setWeights(ArrayList<Double> weights) {
        this.weights = weights;
    }

    //A função getOutput calcula o net do neuronio e aplica a função de transferencia
    public double getOutput() {
        //Calcular net
        double net = 0;

        //para cada entrada
        for (int i = 0; i < this.inputs.size(); i++) {
            //net = somatorio(entrada[i]*peso[i])
            net += this.inputs.get(i) * this.weights.get(i);
        }
        //Aplicar função de transferência
        double output;
        
        if (this.function == 0) { //logistic = 1/(1+e^(-x))
            output = 1 / (1 + Math.pow(Math.E, (-net)));
        } else { //hyperbolic tangent = 1-e^(-2x)/1+e^(-2x)
            output = (1 - Math.pow(Math.E, (-2 * net))) / (1 + Math.pow(Math.E, (-2 * net)));
        }
        //Gerar output
        return output;
    }

    public void setInputs(ArrayList<Double> inputs) {
        this.inputs = inputs;
    }

    @Override
    public String toString() {

        String weightsStr = "";
        for (int i = 0; i < this.weights.size(); i++) {
            weightsStr += this.weights.get(i) + "   ";
        }

        return weightsStr;
    }

}
