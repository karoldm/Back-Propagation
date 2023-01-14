package com.mycompany.rn.project;

/**
 * Função logistica para a função de propagação
 */
public class Logistic extends Function {

    //f(x)
    @Override
    public double compute(double x) {
        return ((double) 1) / (1 + Math.exp(-x));
    }

    //f'(x)
    @Override
    public double derivada(double x) {
        return compute(x) * (1 - compute(x));
    }

    @Override
    public double menorValorImagem() {
        return 0;
    }

    @Override
    public double maiorValorImagem() {
        return 1;
    }
}
