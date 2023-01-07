package com.mycompany.rn.project;

import java.util.ArrayList;

/**
 *
 * @author karol
 */
public class NeuralNetwork {

    private int classAmount;
    private int attributesAmount;
    private int neuronsOcultLayer;
    private final ArrayList<ArrayList<Integer>> matrixAttributes;
    private int function; //0 = logistic and 1 = hyperbolic tangent
    private int stop; //0 = max error and 1 = number of iterations
    private double stopNumber;
    private double learningRate;

    public NeuralNetwork() {
        this.matrixAttributes = new ArrayList<>();
        this.classAmount = 0;
        this.attributesAmount = 0;
        this.neuronsOcultLayer = 0;
        this.function = 0;
        this.stop = 0;
        this.stopNumber = 0;
        this.learningRate = 0.1;
    }

    public double getLearningRate() {
        return learningRate;
    }

    public void setLearningRate(double learningRate) {
        this.learningRate = learningRate;
    }

    public double getStopNumber() {
        return stopNumber;
    }

    public void setStopNumber(double stopNumber) {
        this.stopNumber = stopNumber;
    }

    public int getStop() {
        return stop;
    }

    public void setStop(int stop) {
        this.stop = stop;
    }

    public int getFunction() {
        return function;
    }

    public void setFunction(int function) {
        this.function = function;
    }

    public int getClassAmount() {
        return classAmount;
    }

    public void setClassAmount(int classAmount) {
        this.classAmount = classAmount;
    }

    public int getAttributesAmount() {
        return attributesAmount;
    }

    public void setAttributesAmount(int attributesAmount) {
        this.attributesAmount = attributesAmount;
    }

    public int getNeuronsOcultLayer() {
        return neuronsOcultLayer;
    }

    public void setNeuronsOcultLayer(int neuronsOcultLayer) {
        this.neuronsOcultLayer = neuronsOcultLayer;
    }

    public void addAttributeRow(ArrayList<Integer> row) {
        matrixAttributes.add(row);
    }

    public void calculateClassAmount() {
        int sum = 0;

        int sizeMatrix = matrixAttributes.size();

        ArrayList<Integer> row = matrixAttributes.get(sizeMatrix - 1);

        int sizeRow = row.size();

        int class1 = row.get(sizeRow - 1);

        for (ArrayList<Integer> rowInterator : matrixAttributes) {
            if (class1 != rowInterator.get(sizeRow - 1)) {
                sum++;
            }
            class1 = rowInterator.get(sizeRow - 1);
        }

        setClassAmount(sum);
    }

    public int calculateNeuronsOcultLayer() {
        return (int) Math.round(Math.sqrt(classAmount * attributesAmount));
    }

    private boolean getStopCondiction(double error, int iterations) {
        if (stop == 0 && error < stopNumber) { //maxError
            return false;
        }
        return !(stop == 1 && iterations >= stopNumber);
    }

    public void initTraining() {
        
        OcultLayer ocultLayer = new OcultLayer(neuronsOcultLayer, attributesAmount, function);

        //iniciando neuronios da camada de saída
        OutputLayer outputLayer = new OutputLayer(classAmount, neuronsOcultLayer, function);

        //Separando entrada de dados
        ArrayList<Integer> classeDesejada = new ArrayList<>();
        ArrayList<ArrayList<Double>> inputs = new ArrayList<>();

        for (int i = 0; i < matrixAttributes.size(); i++) {

            inputs.add(new ArrayList<>());

            //Selecionando a classe desejada 
            ArrayList<Integer> row = matrixAttributes.get(i);
            classeDesejada.add(row.get(attributesAmount - 1));

            for (int j = 0; j < row.size() - 2; j++) {
                inputs.get(i).add((double) row.get(j));
            }

        }

        //inputs[i] contém X1 X2 X3 X4 ... e classeDesejada[i] contém CLASSE
        double error = 10000;
        int iterations = 0;
        while (getStopCondiction(error, iterations)) {

            //para cada linha de atributos
            for (int i = 0; i < inputs.size(); i++) {

                //calculando saidas dos neuronios da camada oculta 
                ocultLayer.setInputs(inputs.get(i));

                //calculando saidas dos neuronios da camada de saída 
                outputLayer.setInputs(inputs.get(i));

                //calculando erros e atualizando pesos dos neuronios da camada de saída
                outputLayer.updateWeights(classeDesejada.get(i), learningRate);

                //calculando erros e atualizando pesos dos neuronios da camada oculta
                ocultLayer.updateWeights(learningRate, inputs.get(i));
            }

            //calcular erro da rede
            error = outputLayer.getNetworkError();
            //incrementando iterações
            iterations++;
        }

    }

}
