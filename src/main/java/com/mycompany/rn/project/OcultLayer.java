package com.mycompany.rn.project;

import java.util.ArrayList;

/**
 *
 * @author karol
 */
public class OcultLayer extends Layer {

    public OcultLayer(int neuronsAmount, int weightsAmount, int function) {
        super(neuronsAmount, weightsAmount, function);
    }

    public void updateWeights(double learningRate, ArrayList<Double> input) {
        errors.clear();
        double error;

        //para cada neuronio na camada oculta
        for (int l = 0; l < outputs.size(); l++) {
            //erro
            double sumErrorOutputLayer = 0;

            for (int n = 0; n < neurons.size(); n++) {
                Neuron neuron = neurons.get(n);
                sumErrorOutputLayer += errors.get(n) * neuron.getWeights().get(n);
            }

            if (this.function == 0) { //logistic
                error
                        = sumErrorOutputLayer * (outputs.get(l)
                        * (1 - outputs.get(l)));
            } else { //hyperbolic tangent
                error
                        = sumErrorOutputLayer * (1 - Math.pow(outputs.get(l), 2));
            }

            errors.add(error);
        }

        //atualizando pesos da camada oculta
        for (int x = 0; x < neurons.size(); x++) {
            Neuron neuron = neurons.get(x);
            ArrayList<Double> currentWeights = neuron.getWeights();

            ArrayList<Double> newWeights = new ArrayList<>();
            double newWeight;
            for (int y = 0; y < currentWeights.size(); y++) {
                newWeight = currentWeights.get(y)
                        + (learningRate * errors.get(x)
                        * input.get(y));

                newWeights.add(newWeight);
            }

            neuron.setWeights(newWeights);
        }
    }
}
