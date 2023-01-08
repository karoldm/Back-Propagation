package com.mycompany.rn.project;

import java.util.ArrayList;

/**
 *
 * @author karol
 */
public class OutputLayer extends Layer {

    public OutputLayer(int neuronsAmount, int weightsAmount, int function) {
        super(neuronsAmount, weightsAmount, function);
    }
    
    public void calcErrors(double classeDesejada){
         errors = new ArrayList<>();
        double error;
        //para cada neuronio na camada
        for (int i = 0; i < neuronsAmount; i++) {
            //erro
            
            double desejado = 0;
            if(classeDesejada == i+1 && function == 0){
                desejado = 1;
            }
            else if(classeDesejada != i+1 && function == 0){
                desejado = 0;
            }
            if(classeDesejada == i+1 && function == 0){
                desejado = 1;
            }
            else if(classeDesejada != i+1 && function == 0){
                desejado = -1;
            }
            
            error = desejado - outputs.get(i);

            if (this.function == 0) { //logistic
                error *= (outputs.get(i) * (1 - outputs.get(i)));
            } else { //hyperbolic tangent
                error *= (1 - (outputs.get(i) * outputs.get(i)));
            }

            errors.add(error);
        }
    }

    public void updateWeights(double learningRate, ArrayList<Double> outputOcultLayer) {
        //atualizando pesos da camada de saída
        for (int i = 0; i < this.neurons.size(); i++) {
            Neuron neuron = this.neurons.get(i);
            ArrayList<Double> currentWeights = neuron.getWeights();

            ArrayList<Double> newWeights = new ArrayList<>();
            double newWeight;

            for (int j = 0; j < currentWeights.size(); j++) {
                newWeight = currentWeights.get(j)
                        + (learningRate * this.errors.get(i)
                        * outputOcultLayer.get(j));

                newWeights.add(newWeight);
            }

            neuron.setWeights(newWeights);
        }

    }

    public double getNetworkError() {
        double sumError = 0;
        for (int i = 0; i < errors.size(); i++) {
            sumError += errors.get(i) * errors.get(i);
        }
        return (0.5 * sumError);
    }
}
