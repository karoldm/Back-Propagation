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

    public void updateWeights(int classeDesejada, double learningRate) {
        errors.clear();
        double error;
        //para cada neuronio na camada
        for (int i = 0; i < neuronsAmount; i++) {
            //erro
            error
                    = ((i + 1 == classeDesejada
                            ? classeDesejada : 0)
                    - (outputs.get(i)));

            if (this.function == 0) { //logistic
                error *= (outputs.get(i) * (1 - outputs.get(i)));
            } else { //hyperbolic tangent
                error *= (1 - Math.pow(outputs.get(i), 2));
            }

            errors.add(error);
        }

        //atualizando pesos da camada de saída
        for (int i = 0; i < neurons.size(); i++) {
            Neuron neuron = neurons.get(i);
            ArrayList<Double> currentWeights = neuron.getWeights();

            ArrayList<Double> newWeights = new ArrayList<>();
            double newWeight;
            for (int j = 0; j < currentWeights.size(); j++) {
                newWeight = currentWeights.get(j)
                        + (learningRate * errors.get(i)
                        * outputs.get(j));

                newWeights.add(newWeight);
            }

            neuron.setWeights(newWeights);
        }

    }

    public double getNetworkError() {
        double sumError = 0;
        for (int z = 0; z < errors.size(); z++) {
            sumError += Math.pow(errors.get(z), 2);
        }
        return (0.5 * sumError);
    }
}
