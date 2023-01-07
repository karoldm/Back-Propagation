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
        for (int i= 0; i < outputs.size(); i++) {
            //erro
            double sumErrorOutputLayer = 0;

            for (int n = 0; n < neurons.size(); n++) {
                Neuron neuron = neurons.get(n);
                sumErrorOutputLayer += errors.get(n) * neuron.getWeights().get(n);
            }

            if (this.function == 0) { //logistic
                error
                        = sumErrorOutputLayer * (outputs.get(i)
                        * (1 - outputs.get(i)));
            } else { //hyperbolic tangent
                error
                        = sumErrorOutputLayer * (1 - Math.pow(outputs.get(i), 2));
            }

            errors.add(error);
        }

        //atualizando pesos da camada oculta
        for (int i = 0; i < neurons.size(); i++) {
            Neuron neuron = neurons.get(i);
            ArrayList<Double> currentWeights = neuron.getWeights();

            ArrayList<Double> newWeights = new ArrayList<>();
            double newWeight;
            for (int j = 0; j < currentWeights.size(); j++) {
                newWeight = currentWeights.get(j)
                        + (learningRate * errors.get(i)
                        * input.get(j));

                newWeights.add(newWeight);
            }

            neuron.setWeights(newWeights);
        }
    }
}
