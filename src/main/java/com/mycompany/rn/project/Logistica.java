package com.mycompany.rn.project;

/**
 * Função logistica para a função de propagação
 */
public class Logistica extends Função {

    /**
     * Calcula f(x)
     */
    @Override
    public double compute(double x) {
        return ((double) 1) / (1 + Math.exp(-x));
    }

    /**
     * Calcula f'(x)
     */
    @Override
    public double derivada(double x) {
        return compute(x) * (1 - compute(x));
    }

    @Override
    /**
     * Menor valor da imagem da função, usada para definir a saida das camadas*
     */
    public double menorValorImagem() {
        return 0;
    }

    @Override
    /**
     * Maior valor da imagem da função, usada para definir a saida das camadas*
     */
    public double maiorValorImagem() {
        return 1;
    }
}
