package com.mycompany.rn.project;

import java.util.ArrayList;
import java.util.Random;


/*Classe que representa um neuronio da rede*/
public class Neuron {
    /**Função de propagação utilizada neste neuronio*/
    protected Função funçãoPropagação;
    
    /**Pesos das entradas deste neuronio*/
    protected double[] pesos;
    
    /**Net calculado por este neuronios*/
    protected double net;
    
    /**Valor calculada da propagação deste neuronio*/
    protected double propagação;
    
    /**Erro calculado por este neuronio*/
    protected double erro;
    
    /**Valor dos sinais que veio como entrada para este neuronio*/
    protected double[] inputs;
    
    /**
     * Constroi este neuronio com a determinada função de propagação.
     * O parametro numPesos deve bater com o numero de neuronios da camada anterior.
     * Os pesos são setados aleatoriamente de acordo com uma distribuição gaussiana
     * com media 0 e desvio padrão 0
     * @param funçãoPropagação função de propagação deste neuronio
     * @param numPesos numero de pesos que este neuronio possui
     */
    public Neuron(Função funçãoPropagação, int numPesos){
        this.funçãoPropagação = funçãoPropagação;
        pesos = new double[numPesos];
        for(int i=0;i<numPesos;i++){
            pesos[i] = new Random().nextGaussian();
        }
    }
    
    /**
     * Calcula o valor de propagação deste neuronio.
     * @param entradas sinais de entrada
     * @return retonar o valor de propagação.
     */
    public double calcularPropagação(double[] entradas){
        net = 0;
        inputs = entradas;
        for(int i=0;i<pesos.length;i++){
            net += pesos[i]*entradas[i];
        }
        propagação = funçãoPropagação.compute(net);
        return propagação;
    }

    /**
     * Calcula o erro deste neuronio.
     * Deve ser usado para neuronios da camada oculta.
     * @param soma
     * @return 
     */
    public double calculaErroOculta(double soma) {
        erro = funçãoPropagação.derivada(net)*soma;
        return erro;
    }

    /**
     * Calcula o erro deste neuronio.
     * Deve ser usado para neuronios da camada de saida.
     * @param desejado valor desejado deste neuronio de saida.
     * @return retorna o erro deste neuronio.
     */
    public double calculaErroSaida(double desejado) {
        erro = (desejado - propagação)*funçãoPropagação.derivada(net);
        return erro;
    }

    /**
     * Ajusta os pesos deste neuronio de acordo com a taxa do parametro e com
     * os erros já calclados.
     * Deve ser chamado após os erros deste neuronio terem sidos calculados e 
     * de preferencia após toda a rede ou a camada anterior a esta ter calculado
     * seus erros.
     * @param taxaAprendizado taxa de aprendizado usado para ajustar os pesos.
     */
    public void ajustarPesos(double taxaAprendizado) {
        for(int i=0;i<pesos.length;i++){
            pesos[i] = pesos[i] + taxaAprendizado*erro*inputs[i];
        }
    }
    
}
