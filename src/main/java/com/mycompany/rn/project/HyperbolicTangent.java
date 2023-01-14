
package com.mycompany.rn.project;

/**
 *
 * @author karol
 */
public class HyperbolicTangent extends Function {
    
    //f(x)
    @Override
    public double compute(double x) {
        return (1 - Math.exp(-2 * x)) / (1 + Math.exp(-2 * x));
    }

    //f'(x)
    @Override
    public double derivada(double x) {
        return 1 - Math.pow(compute(x), 2);
    }

    @Override
    public double menorValorImagem() {
        return -1;
    }

    @Override
    public double maiorValorImagem() {
        return 1;
    }
}
