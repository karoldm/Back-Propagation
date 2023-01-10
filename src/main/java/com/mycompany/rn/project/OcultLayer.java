package com.mycompany.rn.project;

import java.util.ArrayList;

/**
 *
 * @author karol
 * Classe para representar uma camada oculta
 * Toda camada oculta é filha de uma camada e possui um método para calcular 
 * os erros e atualizar os pesos dos neuronios (a camada oculta e de saída fazem
 * esses cálculos de forma diferente)
 */
public class OcultLayer extends Layer {

    public OcultLayer(int neuronsAmount, int weightsAmount, int function) {
        super(neuronsAmount, weightsAmount, function);
    }

    //Método para calcular o erro de cada neuronio da camada
    public void calcErrors(ArrayList<Neuron> neuronsOutput, ArrayList<Double> errorsOutputLayer) {
        errors = new ArrayList<>();
        double error;

        //para cada neuronio na camada oculta
        for (int i = 0; i < neurons.size(); i++) {
            //erro
            double sumErrorOutputLayer = 0;

            //Soma dos erros dos neuronios de cada neuronio da camada de saída multiplicado pelo
            //peso correspondente ao neuronio da camada oculta atual
            for (int j = 0; j < neuronsOutput.size(); j++) {
                Neuron neuronOutput = neuronsOutput.get(j);
                sumErrorOutputLayer += errorsOutputLayer.get(j) * neuronOutput.getWeights().get(i);
            }

            if (this.function == 0) { //logistic' = (saída)*(1-saída)
                error
                        = sumErrorOutputLayer * (this.outputs.get(i)
                        * (1 - this.outputs.get(i)));
            } else { //hyperbolic tangent' = 1-(saída)^2
                error
                        = sumErrorOutputLayer * (1 - (this.outputs.get(i) * this.outputs.get(i)));
            }

            errors.add(error);
        }
    }

    
    //Método para atualizar os pesos da camada oculta
    public void updateWeights(double learningRate, ArrayList<Double> input) {

        //atualizando pesos da camada oculta
        
        //Para cada neuronio da camada oculta
        for (int i = 0; i < this.neurons.size(); i++) {
            
            Neuron neuron = this.neurons.get(i);
            //pesos atuais desse neuronio
            ArrayList<Double> currentWeights = neuron.getWeights();

            //array para armazenar novos pesos
            ArrayList<Double> newWeights = new ArrayList<>();
            double newWeight;
            
            //Para cada peso desse neuronio
            for (int j = 0; j < currentWeights.size(); j++) {
                //w = w + (n*erro*entrada)
                newWeight = currentWeights.get(j)
                        + (learningRate * this.errors.get(i)
                        * input.get(j));

                newWeights.add(newWeight);
            }

            neuron.setWeights(newWeights);
        }
    }
}
