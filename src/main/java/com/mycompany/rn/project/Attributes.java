package com.mycompany.rn.project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;

/**
 *
 * @author karol
 * Classe que representa a matriz de atributos dos conjuntos de treinamento e teste
 */
public class Attributes {

    //linhas da matriz
    private ArrayList<AttributeRow> attributes = new ArrayList<>();

    //Saída esperada mapeada para a rede
    //Por exemplo: classe desejada = 5 então saída = [0, 0, 0, 0, 1] se a função for logistica
    //e saída = [-1, -1, -1, -1, 1] se a função for tangente hiperbolica
    private final HashMap<String, Double[]> outputsMap = new HashMap<>(10);

    //classes do conjunto
    private final ArrayList<String> classes = new ArrayList<>();

    private int numClasses = 0;

    
    /**
     * Método Construtor
     * @param inputs matriz de atributos lida do CSV no formato X1,X2,X3,...,Xn,CLASSE
     */
    public Attributes(double[][] inputs) {
        
        for (double[] row : inputs) {
            double[] atributos = new double[row.length];
             for(int i = 0; i <row.length;i++ ){
                 atributos[i] = row[i];
             }
            attributes.add(new AttributeRow(atributos, String.valueOf(atributos[atributos.length-1])));
            outputsMap.putIfAbsent(String.valueOf(atributos[atributos.length-1]), null);
        }
        this.numClasses = outputsMap.size();
    }

    //Retorna a linha i da matriz de atributos: X1,X2,X3,..,Xn,CLASSE
    public AttributeRow getAttributeRow(int i) {
        return attributes.get(i);
    }

    //Retorna a quantidade de linhas da matriz de atributos
    public int size() {
        return attributes.size();
    }

    //Retorna os atributos da linha i da matriz, ou seja, X1,X2,X3,..,Xn sem a CLASSE
    public double[] getAtributos(int i) {
        return attributes.get(i).atributos;
    }

    //Embaralha as linhas para treinar a rede melhor
    public void embaralhar() {
        ArrayList<AttributeRow> novaInstancias = new ArrayList<>();
        Random r = new Random();
        while (!attributes.isEmpty()) {
            novaInstancias.add(attributes.remove(r.nextInt(attributes.size())));
        }
        attributes = novaInstancias;
    }

    //Retorna a saída desejada para a linha i
    public Double[] getSaida(int i) {
        return outputsMap.get(attributes.get(i).classe);
    }

    /**
     * Recupera o minimo e maximo de cada atributo do conjunto 'attributes' e retorna nos
     * vetores de entrada
     */
    private static void recuperaMinMax(double[] min, double[] max, Attributes attributes) {
        if (attributes == null) {
            return;
        }
        for (AttributeRow i : attributes.attributes) {
            double[] atributos = i.atributos;
            for (int cont = 0; cont < atributos.length; cont++) {
                if (atributos[cont] < min[cont]) {
                    min[cont] = atributos[cont];
                }
                if (atributos[cont] > max[cont]) {
                    max[cont] = atributos[cont];
                }
            }
        }
    }

    /**
     * Normaliza cada instancia do conjunto 'attributes'
     *
     * @param min menor valor de cada atributo do conjunto
     * @param max maior valor de cada stributo do conjunto
     * @param limiteMin menor valor do intervalo após normalização
     * @param limiteMax maior valor do intervalo após normalização
     * @param attributes conjunto que será normalizado
     */
    private static void normaliza(double[] min, double[] max, double limiteMin, double limiteMax, Attributes attributes) {
        if (attributes == null) {
            return;
        }
        for (AttributeRow i : attributes.attributes) {
            i.normalizar(min, max, limiteMin, limiteMax);
        }
    }

    /**
     * Normaliza este conjunto para que os atributos estejam entre
     * [limiteMin,limiteMax]
     *
     * @param c Conjunto que será normalizado junto com este.
     */
    public void normalizar(double limiteMin, double limiteMax, Attributes c) {
        int numAtr = attributes.get(0).atributos.length;
        double min[] = new double[numAtr];
        double max[] = new double[numAtr];
        for (int i = 0; i < numAtr; i++) {
            min[i] = Double.MAX_VALUE;
            max[i] = -Double.MAX_VALUE;
        }
        recuperaMinMax(min, max, this);
        recuperaMinMax(min, max, c);
        normaliza(min, max, limiteMin, limiteMax, c);
        normaliza(min, max, limiteMin, limiteMax, this);
    }

    /**
     * Define as saidas esperadas pela rede para cada classe do conjunto.
     *
     * @param funçãoPropagação A função é usada para definir o minimo e o maximo
     * da saida.
     */
    public void definirSaidasClasses(Function funçãoPropagação) {
        Set<String> valorClasses = outputsMap.keySet();
        int cont = 0;
        for (String i : valorClasses) {
            Double[] saidaClasses = new Double[outputsMap.size()];
            for (int j = 0; j < outputsMap.size(); j++) {
                saidaClasses[j] = funçãoPropagação.menorValorImagem();
            }
            saidaClasses[cont++] = funçãoPropagação.maiorValorImagem();
            outputsMap.put(i, saidaClasses);
            classes.add(i);
        }
    }

    public int getNumClasses() {
        return numClasses;
    }

    public int getIndexClasse(int i) {
        return classes.indexOf(attributes.get(i).classe);
    }
}
