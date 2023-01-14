package com.mycompany.rn.project;

//Classe que representa uma função de propagação
public abstract class Function {
    //calcula f(x)
    public abstract double compute(double x);
    //calcula f'(x)
    public abstract double derivada(double x);
    //calcula o menor valor da imagem da função, usada para definir a saida das camadas
    public abstract double menorValorImagem();
    //calcula o maior valor da imagem da função, usada para definir a saida das camadas
    public abstract double maiorValorImagem();
}
