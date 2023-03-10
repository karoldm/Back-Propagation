package com.mycompany.rn.project;

/**
 *
 * @author karol
 * Classe para representar uma camada de neuronios 
 * Toda camada possui um array de neuronios
 * E os erros desse neuronio
 */
public class Layer {

   //Neuronios da camada 
    protected Neuron[] neurons;
    
    //Erro dos neuronios 
    protected double[] erros;
    
    /**
     * Método Construtor
     * @param numNeuroniosOcultos Numero de neuronios desta camada
     * @param function Função de propagação usadas nos neuronios
     * @param numPesos Numero de pesos que cada neuronios deve ter
     */
    public Layer(int numNeuroniosOcultos, Function function, int numPesos) {
        neurons = new Neuron[numNeuroniosOcultos];
        erros = new double[numNeuroniosOcultos];
        for(int i=0;i<neurons.length;i++){
            neurons[i] = new Neuron(function,numPesos);
        }
    }

    /**
     * Método para o processo de feedFoward/realimentação desta camada
     * @param inputs entrada recebida por esta camada
     * @return retonar os sinais propagados por esta camada
     */
    public double[] feedFoward(double[] inputs) {
        double[] sinais = new double[neurons.length];
        for(int i=0;i<neurons.length;i++){
            sinais[i] = neurons[i].calcularSaida(inputs);
        }
        return sinais;
    }

    /**
     * Método para calcular os erros destes neuronios considerandos 
     * as saidas desejadas (camada de saída)
     * @param outputEsperado saidas esperadas por essa camada
     */
    public void calculaErros(Double[] outputEsperado) {
        for(int i=0;i<neurons.length;i++){
            erros[i] = neurons[i].calculaErroSaida(outputEsperado[i]);
        }
    }

    public double[] getErros(){
        return erros;
    }

    /**
     * Método para calcular os erros destes neuronios (camada oculta)
     * @param saida camada de saída
     */
    public void calculaErros(Layer saida) {
        for(int i=0;i<neurons.length;i++){
            double soma = saida.somaErros(i);
            neurons[i].calculaErroOculta(soma);
        }
    }

    /**
     * Método que retorna a soma dos erros destes neuronios "i" multiplicados pelo peso Wij
     * Formula: somatorio(de i=0 até m)(erro(i)*Wij) 
     * @param j indice do neuronio da camada oculta anterior para o qual está sendo 
     * calculando o erro
     * @return retorna a soma 
     */
    private double somaErros(int j) {
        double soma =0;
        for(int i=0;i<neurons.length;i++){
            soma+=neurons[i].erro*neurons[i].pesos[j];
        }
        return soma;
    }
    
    /**
     * Método para ajustar os pesos desse neuronio utilizando a taxa de aprendizado 
     * @param taxaAprendizado Taxa de aprendizado utilizado para ajustar os pesos
     */
    public void ajustarPesos(double taxaAprendizado) {
        for(int i=0;i<neurons.length;i++){
            neurons[i].ajustarPesos(taxaAprendizado);
        }
    }

    /**
     * @return Retorna o erro da rede, dado pela fórmula: 1/2 * Σ (de i=0 até o)(erro(i)^2)
     */
    public double erroRede() {
        double soma = 0;
        for(int i=0;i<neurons.length;i++){
            soma+=Math.pow(neurons[i].erro, 2);
        }
        return soma/2;
    }

    /**
     * Retorna os pesos do neuronio
     * @param neuronio Neuronio que se deseja recuperar os pesos
     */
    public double[] getPesos(int neuronio) {
        return neurons[neuronio].pesos;
    }
}
