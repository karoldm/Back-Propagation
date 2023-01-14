package com.mycompany.rn.project;

import java.util.Random;


//Classe que representa um neuronio da rede
public class Neuron {

    //Função de propagação utilizada (tangente hiperbolica ou logistica)
    protected Function function;

    //pesos
    protected double[] pesos;

    //Net calculado calculado por: somatorio(peso(i)*entrada(i))
    protected double net;

    //Saida do neuronio
    protected double saida;

    //Erro do neuronio calculado por: erro = (desejado-saida))*f'(net)
    protected double erro;
    
    //Valores recebidos nas entradas do neuronio
    protected double[] inputs;

    /**
     * Método Construtor
     * numPesos deve ser igual ao numero de neuronios da camada anterior
     * Os pesos são aleatórios 
     * @param function função de propagação deste neuronio
     * @param numPesos numero de pesos que este neuronio possui
     */
    public Neuron(Function function, int numPesos) {
        this.function = function;
        pesos = new double[numPesos];
        for (int i = 0; i < numPesos; i++) {
            pesos[i] = new Random().nextGaussian();
        }
    }

    /**
     * Método para calcular a saida deste neuronio.
     * @param entradas valores de entrada
     * @return retonar o valor de propagação/saída.
     */
    public double calcularSaida(double[] entradas) {
        net = 0;
        inputs = entradas;
        for (int i = 0; i < pesos.length; i++) {
            net += pesos[i] * entradas[i];
        }
        saida = function.compute(net);
        return saida;
    }

    /**
     * Métodos para calcular o erro deste neuronio (camada oculta)
     * @param soma
     * @return
     */
    public double calculaErroOculta(double soma) {
        erro = function.derivada(net) * soma;
        return erro;
    }

    /**
     * Métodos para calcular o erro deste neuronio (camada de saída)
     * @param desejado valor desejado deste neuronio de saida.
     * @return retorna o erro deste neuronio.
     */
    public double calculaErroSaida(double desejado) {
        erro = (desejado - saida) * function.derivada(net);
        return erro;
    }

    /**
     * Método para ajustar os pesos deste neuronio de acordo com a taxa de aprendizado 
     * passada como parametro
     * @param taxaAprendizado taxa de aprendizado usado para ajustar os pesos.
     */
    public void ajustarPesos(double taxaAprendizado) {
        for (int i = 0; i < pesos.length; i++) {
            pesos[i] = pesos[i] + taxaAprendizado * erro * inputs[i];
        }
    }

}
