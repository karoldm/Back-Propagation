package com.mycompany.rn.project;

import java.util.Random;

/**
 *
 * @author karol Classe que representa a rede neural
 */
public class NeuralNetwork {

    /**
     * Usado para gerar os pesos aleatorios dos neuronios
     */
    public static final Random rand = new Random();

    /**
     * Função de propagação usada pelos neuronios da rede
     */
    protected Function funçãoPropagação;

    /**
     * Camadas de neuronios ocultos usados para o processamento
     */
    protected Layer[] oculta;

    /**
     * Camada de neuronios na saida
     */
    protected Layer saida;

    /**
     * Taxa de aprendizado para esta rede
     */
    protected double taxaAprendizado = 0.1;

    /**
     * Numero de iterações para parar o treinamento caso a rede não convergir.
     * Caso o erro da rede seja menor que <code>limiar</code> o treinamento para
     * antes
     */
    protected int numIteraçõesLimite = 20000;

    /**
     * Limiar usado para parar o treinamento antes do limite de iterações. O
     * treinamento para caso o valor do erro seja menor que o limiar
     */
    protected double limiar = 0.000001f;

    /**
     * Cria a rede neural e instancia as camadas e os neuronios da rede. Os
     * pesos dos neuronios são decididos aleatoriamente seguindo uma
     * distribuição gaussiana com media 0 e desvio padrão 1.
     *
     * As camadas ocultas possuem a mesma quantidade de neuronios.
     *
     * @param numNeuroniosEntrada Numero de neuronios na camada de entrada
     * @param numNeuroniosOcultos Numero de neuronios na camada oculta
     * @param numCamadasOcultas Numero de camadas ocultas
     * @param numNeuroniosSaida Numero de neuronios na camada de saida
     * @param propagação Função de propagação que será usado na rede
     */
    public NeuralNetwork(int numNeuroniosEntrada, int numNeuroniosOcultos, int numCamadasOcultas, int numNeuroniosSaida, Function propagação) {
        oculta = new Layer[numCamadasOcultas];
        oculta[0] = new Layer(numNeuroniosOcultos, propagação, numNeuroniosEntrada);
        for (int i = 1; i < oculta.length; i++) {
            oculta[i] = new Layer(numNeuroniosOcultos, propagação, numNeuroniosOcultos);
        }
        saida = new Layer(numNeuroniosSaida, propagação, numNeuroniosOcultos);
        funçãoPropagação = propagação;
    }

    /**
     * Realiza a alimentação na rede com a entrada especificada no parametro
     *
     * @param inputs entrada da rede
     * @return retorna a saida da rede
     */
    public double[] feedFoward(double[] inputs) {
        double[] sinais = inputs;
        for (int i = 0; i < oculta.length; i++) {
            sinais = oculta[i].feedFoward(sinais);
        }
        sinais = saida.feedFoward(sinais);
        return sinais;
    }

    /**
     * Realiza uma iteração na rede. A iteração é composta de um feedfoward e um
     * backpropagation para uma entrada.
     *
     * @param inputs entrada para a iteração
     * @param outputEsperado resultado esperado
     * @return retorna o erro da rede nesta iteração.
     */
    public double iteration(double[] inputs, Double[] outputEsperado) {
        double[] output = feedFoward(inputs);
        backPropagation(output, outputEsperado);
        return erroRede();
    }

    /**
     * Realiza o processo de treinamento da rede com as instancias do parametro
     *
     * @param instancias instancias utilizadas para o treinamento
     */
    public void treinamento(Attributes attributes) {
        boolean houveErro;
        double maiorErro;
        attributes.definirSaidasClasses(funçãoPropagação);
        for (int i = 0; i < numIteraçõesLimite; i++) {
            houveErro = false;
            double erroAtual;
            for (int j = 0; j < attributes.size(); j++) {
                erroAtual = iteration(attributes.getAtributos(j), attributes.getSaida(j));
                if (erroAtual > limiar) {
                    houveErro = true;
                }
            }
            if (!houveErro) {
                return;
            }
        }

    }

    /**
     * Testa a rede com o conjunto do parametro e retorna a matriz de confusão
     *
     * @param instancias conjunto de testes
     * @return matriz de confusão
     */
    public int[][] testarRede(Attributes attributes) {
        int numClasses = attributes.getNumClasses();
        attributes.definirSaidasClasses(funçãoPropagação);
        int[][] matrizConfusão = new int[numClasses][numClasses];
        for (int i = 0; i < numClasses; i++) {
            for (int j = 0; j < numClasses; j++) {
                matrizConfusão[i][j] = 0;
            }
        }
        for (int i = 0; i < attributes.size(); i++) {
            double[] saida = feedFoward(attributes.getAtributos(i));
            int classeCalculada = indexMaiorSinalSaida(saida);
            int classeDesejada = attributes.getIndexClasse(i);
            matrizConfusão[classeCalculada][classeDesejada]++;
        }
        return matrizConfusão;
    }

    /**
     * Processo de backpropagation da rede
     *
     * @param outputs Saida calculada no final do processo feedFoward
     * @param outputEsperado Saida esperada pela rede
     */
    public void backPropagation(double[] outputs, Double[] outputEsperado) {
        saida.calculaErros(outputEsperado);
        oculta[oculta.length - 1].calculaErros(saida);
        for (int i = oculta.length - 2; i >= 0; i--) {
            oculta[i].calculaErros(oculta[i + 1]);
        }
        saida.ajustarPesos(taxaAprendizado);
        for (int i = 0; i < oculta.length; i++) {
            oculta[i].ajustarPesos(taxaAprendizado);
        }
    }

    /**
     * @return Retorna o erro da rede para a iteração atual
     */
    private double erroRede() {
        return saida.erroRede();
    }

    /**
     * Verifica em qual indice se encontra a maior valor na saida da rede
     *
     * @param saida
     * @return
     */
    private int indexMaiorSinalSaida(double[] saida) {
        int pos = 0;
        double maior = Integer.MIN_VALUE;
        for (int i = 0; i < saida.length; i++) {
            if (maior < saida[i]) {
                maior = saida[i];
                pos = i;
            }
        }
        return pos;
    }

    /**
     * Retorna os pesos de um neuronio
     *
     * @param camada indice da camada
     * @param neuronio indice do neuronio
     * @return
     */
    public double[] getPesos(int camada, int neuronio) {
        if (oculta.length == camada) {
            return saida.getPesos(neuronio);
        }
        return oculta[camada].getPesos(neuronio);
    }

    public double getTaxaAprendizado() {
        return taxaAprendizado;
    }

    public void setTaxaAprendizado(double taxaAprendizado) {
        this.taxaAprendizado = taxaAprendizado;
    }

    public int getNumIteraçõesLimite() {
        return numIteraçõesLimite;
    }

    public void setNumIteraçõesLimite(int numIteraçõesLimite) {
        this.numIteraçõesLimite = numIteraçõesLimite;
    }

    public double getLimiar() {
        return limiar;
    }

    public void setLimiar(double limiar) {
        this.limiar = limiar;
    }

}
