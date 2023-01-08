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

    public void calcErrors(ArrayList<Neuron> neuronsOutput, ArrayList<Double> errorsOutputLayer) {
        errors = new ArrayList<>();
        double error;

        //para cada neuronio na camada oculta
        for (int i = 0; i < neurons.size(); i++) {
            //erro
            double sumErrorOutputLayer = 0;

            for (int j = 0; j < neuronsOutput.size(); j++) {
                Neuron neuronOutput = neuronsOutput.get(j);
                sumErrorOutputLayer += errorsOutputLayer.get(j) * neuronOutput.getWeights().get(i);
            }

            if (this.function == 0) { //logistic
                error
                        = sumErrorOutputLayer * (this.outputs.get(i)
                        * (1 - this.outputs.get(i)));
            } else { //hyperbolic tangent
                error
                        = sumErrorOutputLayer * (1 - (this.outputs.get(i) * this.outputs.get(i)));
            }

            errors.add(error);
        }
    }

    public void updateWeights(double learningRate, ArrayList<Double> input) {

        //atualizando pesos da camada oculta
        for (int i = 0; i < this.neurons.size(); i++) {
            Neuron neuron = this.neurons.get(i);
            ArrayList<Double> currentWeights = neuron.getWeights();

            ArrayList<Double> newWeights = new ArrayList<>();
            double newWeight;
            for (int j = 0; j < currentWeights.size(); j++) {
                newWeight = currentWeights.get(j)
                        + (learningRate * this.errors.get(i)
                        * input.get(j));

                newWeights.add(newWeight);
            }

            neuron.setWeights(newWeights);
        }
    }
}
