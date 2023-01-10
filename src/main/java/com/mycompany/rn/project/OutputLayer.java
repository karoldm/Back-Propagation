package com.mycompany.rn.project;

import java.util.ArrayList;

/**
 *
 * @author karol
 * Classe para representar uma camada de saída
 * Toda camada de saída é filha de uma camada e possui um método para calcular 
 * os erros e atualizar os pesos dos neuronios (a camada oculta e de saída fazem
 * esses cálculos de forma diferente)
 */
public class OutputLayer extends Layer {

    public OutputLayer(int neuronsAmount, int weightsAmount, int function) {
        super(neuronsAmount, weightsAmount, function);
    }
    
    //Método para calcular o erro de cada neuronio da camada
    public void calcErrors(double classeDesejada){
         errors = new ArrayList<>();
        double error;
        
        //para cada neuronio na camada de saída
        for (int i = 0; i < neuronsAmount; i++) {
            //o erro é calculado como: (valor desejado - valor obtido)*f'(net)
            
            double desejado = 0;
            
            //se a classe desejada é 4 então as saídas dos neuronios deve ser: 
            //0 0 0 1 0 se a função for logistica e
            //-1 -1 -1 1 -1 se a função for tangente hiperbolica
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

            if (this.function == 0) { //logistic' = (saída)*(1-saída)
                error *= (outputs.get(i) * (1 - outputs.get(i)));
            } else { //hyperbolic tangent' = 1-(saída)^2
                error *= (1 - (outputs.get(i) * outputs.get(i)));
            }

            errors.add(error);
        }
    }

    //função para atualizar os pesos de cada neuronio
    public void updateWeights(double learningRate, ArrayList<Double> outputOcultLayer) {
        //atualizando pesos da camada de saída
        
        //Para cada neuronio na camada de saída
        for (int i = 0; i < this.neurons.size(); i++) {
            
            Neuron neuron = this.neurons.get(i);
            //pesos atuais desse neuronio
            ArrayList<Double> currentWeights = neuron.getWeights();

            //array para armazenar os novos pesos
            ArrayList<Double> newWeights = new ArrayList<>();
            double newWeight;

            //Para cada peso desse neuronio
            for (int j = 0; j < currentWeights.size(); j++) {
                //w = w + (n*erro*saída da camada oculta)
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
