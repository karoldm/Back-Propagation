package com.mycompany.rn.project;

/**
 *
 * @author karol
 * Classe para representar uma camada de neuronios 
 * Toda camada possui um array de neuronios, as entradas e as saídas de cada neurônio
 * e os erros calculados de cada neuronio 
 */
public class Layer {

   /*Conjunto de neuronios desta camada*/
    protected Neuron[] neurons;
    
    /*Armazena os erros calculados pelos neuronios desta camada para uso futuro*/
    protected double[] erros;
    
    /**
     * Constroi a camada de neuronios e instancia os neuronios, setando os pesos
     * aleatoriamente seguinda uma distribuição gaussiana com média 0 e desvio
     * padão 1
     * @param numNeuroniosOcultos Numero de neuronios desta camada
     * @param propagação Função de propagação usadas nos neuronios
     * @param numPesos Numero de pesos que cada neuronios deve ter. Este numero
     * Deve corresponder com o numero de neuronios da camada anterior a esta.
     */
    public Layer(int numNeuroniosOcultos, Função propagação, int numPesos) {
        neurons = new Neuron[numNeuroniosOcultos];
        erros = new double[numNeuroniosOcultos];
        for(int i=0;i<neurons.length;i++){
            neurons[i] = new Neuron(propagação,numPesos);
        }
    }

    /**
     * Processo de feedFoward desta camada.
     * @param inputs entrada recebida por esta camada
     * @return retonar os sinais propagados por esta camada
     */
    public double[] feedFoward(double[] inputs) {
        double[] sinais = new double[neurons.length];
        for(int i=0;i<neurons.length;i++){
            sinais[i] = neurons[i].calcularPropagação(inputs);
        }
        return sinais;
    }

    /**
     * Calcula os erros destes neuronios considerandos as saidas desejadas.
     * Esse método deve ser chamado apenas para a camada de saida.
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
     * Calcula os erros destes neuronios considerandos os erros propagados pela
     * camada posterior a esta.
     * Esse método deve ser chamado apenas para camadas ocultas.
     * @param saida Camada posterior a esta
     */
    public void calculaErros(Layer saida) {
        for(int i=0;i<neurons.length;i++){
            double soma = saida.somaErros(i);
            neurons[i].calculaErroOculta(soma);
        }
    }

    /**
     * Retorna a soma dos erros destes neuronios "i" multiplicados pelo peso Wij
     * Formula: Σ (de i=0 até m)(erro(i)*Wij) 
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
     * Ajusta os pesos desse neuronio utilizando a taxa de aprendizado vindo
     * pelo parametro.
     * Este metodo deve ser chamado após está camada ter calculado seus erros e
     * de preferencia após toda a rede ter calculada seus erros
     * @param taxaAprendizado Taxa de aprendizado utilizado para ajustar os pesos
     */
    public void ajustarPesos(double taxaAprendizado) {
        for(int i=0;i<neurons.length;i++){
            neurons[i].ajustarPesos(taxaAprendizado);
        }
    }

    /**
     * Retorna metade da soma dos quadrados dos erros dos neuronios desta camada
     * Formula: 1/2 * Σ (de i=0 até o)(erro(i)^2)
     * Deve ser chamado apenas para a camada de saida.
     * @return Retorna metade da soma.
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
     * @param neuronio Neuronio no qual será retornado os pesos
     */
    public double[] getPesos(int neuronio) {
        return neurons[neuronio].pesos;
    }
}
