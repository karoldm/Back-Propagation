
package com.mycompany.rn.project;

/**
 *
 * @author karol
 */
public class TangenteHiperbolica extends Função {
    @Override
    public double compute(double x) {
        return (1 - Math.exp(-2 * x)) / (1 + Math.exp(-2 * x));
    }

    @Override
    public double derivada(double x) {
        return 1 - Math.pow(compute(x), 2);
    }

    @Override
    /**
     * Menor valor da imagem da função, usada para definir a saida das camadas*
     */
    public double menorValorImagem() {
        return -1;
    }

    @Override
    /**
     * Maior valor da imagem da função, usada para definir a saida das camadas*
     */
    public double maiorValorImagem() {
        return 1;
    }
}
