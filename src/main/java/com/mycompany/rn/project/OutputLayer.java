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
        for (int l = 0; l < neuronsAmount; l++) {
            //erro
            error
                    = ((l + 1 == classeDesejada
                            ? classeDesejada : 0)
                    - (outputs.get(l)));

            if (this.function == 0) { //logistic
                error *= (outputs.get(l) * (1 - outputs.get(l)));
            } else { //hyperbolic tangent
                error *= (1 - Math.pow(outputs.get(l), 2));
            }

            errors.add(error);
        }

        //atualizando pesos da camada de saída
        for (int x = 0; x < neurons.size(); x++) {
            Neuron neuron = neurons.get(x);
            ArrayList<Double> currentWeights = neuron.getWeights();

            ArrayList<Double> newWeights = new ArrayList<>();
            double newWeight;
            for (int y = 0; y < currentWeights.size(); y++) {
                newWeight = currentWeights.get(y)
                        + (learningRate * errors.get(x)
                        * outputs.get(y));

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
