package com.mycompany.rn.project;

import java.util.ArrayList;
import java.util.Iterator;

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
        if (stop == 1 && iterations >= stopNumber) { //iterations
            return false;
        }
        return true;
    }

    public void initTraining() {
        ArrayList<Neuron> neuronsOcult = new ArrayList<>();
        ArrayList<Neuron> neuronsOutput = new ArrayList<>();
        ArrayList<Double> outputsNeuronsOcult = new ArrayList<>();
        ArrayList<Double> outputsNeuronsOutput = new ArrayList<>();
        ArrayList<Double> errorsOutputLayer = new ArrayList<>();
        ArrayList<Double> errorsOcultLayer = new ArrayList<>();
        double errorOutputLayer;
        double errorOcultLayer;

        //iniciando neuronios da camada oculta
        //cada neuronio da camada oculta possui attributesAmount pesos já que a
        //rede é totalmente concectada
        //os pesos são escolhidos aleatóriamente entre -0.001 e 0.001
        ArrayList<Double> weights = new ArrayList<>();

        for (int i = 0; i < neuronsOcultLayer; i++) {
            weights.clear();
            for (int j = 0; j < attributesAmount; j++) {
                double weight = Math.random() * (0.001 - (-0.001)) + (-0.001);
                weights.add(weight);
            }
            neuronsOcult.add(new Neuron(weights, function));
        }

        //iniciando neuronios da camada de saída
        for (int i = 0; i < classAmount; i++) {
            weights.clear();
            for (int j = 0; j < neuronsOcultLayer; j++) {
                double weight = Math.random() * (0.001 - (-0.001)) + (-0.001);
                weights.add(weight);
            }
            neuronsOutput.add(new Neuron(weights, function));
        }

        //Entrada de dados
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
                outputsNeuronsOcult.clear();
                outputsNeuronsOutput.clear();

                //para cada neuronio da camada oculta
                for (int j = 0; j < neuronsOcult.size(); j++) {
                    Neuron neuron = neuronsOcult.get(j);
                    //inserindo entradas
                    neuron.setInputs(inputs.get(i));
                    //calculando saida de cada neuronio da camada oculta
                    outputsNeuronsOcult.add(neuron.getOutput());
                }

                //calcular saidas dos neuronios da camada de saída 
                for (int k = 0; k < neuronsOutput.size(); k++) {
                    Neuron neuron = neuronsOcult.get(k);

                    //a entrada dos neurônios de saída é a saída dos neurônios da camada oculta
                    neuron.setInputs(outputsNeuronsOcult);

                    //calculando saida de cada neuronio da camada de saída
                    outputsNeuronsOutput.add(neuron.getOutput());

                }

                //calculando erros dos neuronios da camada de saída
                errorsOutputLayer.clear();

                //para cada neuronio na camada de saida
                for (int l = 0; l < outputsNeuronsOutput.size(); l++) {
                    //erro
                    errorOutputLayer
                            = ((l + 1 == classeDesejada.get(i)
                            ? classeDesejada.get(i) : 0)
                            - (outputsNeuronsOutput.get(l)));

                    if (this.function == 0) { //logistic
                        errorOutputLayer *= (outputsNeuronsOutput.get(l) * (1 - outputsNeuronsOutput.get(l)));
                    } else { //hyperbolic tangent
                        errorOutputLayer *= (1 - Math.pow(outputsNeuronsOutput.get(l), 2));
                    }
                    
                    errorsOutputLayer.add(errorOutputLayer);
                }

                //calculando erros dos neuronios da camada oculta
                errorsOcultLayer.clear();

                //para cada neuronio na camada oculta
                for (int l = 0; l < outputsNeuronsOcult.size(); l++) {
                    //erro
                    double sumErrorOutputLayer = 0;

                    for (int n = 0; n < neuronsOutput.size(); n++) {
                        Neuron neuron = neuronsOutput.get(n);
                        sumErrorOutputLayer += errorsOutputLayer.get(n) * neuron.getWeights().get(n);
                    }

                    if (this.function == 0) { //logistic
                        errorOcultLayer
                                = sumErrorOutputLayer * (outputsNeuronsOcult.get(l)
                                * (1 - outputsNeuronsOcult.get(l)));
                    } else { //hyperbolic tangent
                        errorOcultLayer
                                = sumErrorOutputLayer * (1 - Math.pow(outputsNeuronsOcult.get(l), 2));
                    }

                    errorsOcultLayer.add(errorOcultLayer);
                }

                //atualizando pesos da camada de saída
                for (int x = 0; x < neuronsOutput.size(); x++) {
                    Neuron neuron = neuronsOutput.get(x);
                    ArrayList<Double> currentWeights = neuron.getWeights();

                    ArrayList<Double> newWeights = new ArrayList<>();
                    double newWeight;
                    for (int y = 0; y < currentWeights.size(); y++) {
                        newWeight = currentWeights.get(y)
                                + (learningRate * errorsOutputLayer.get(x)
                                * outputsNeuronsOcult.get(y));

                        newWeights.add(newWeight);
                    }

                    neuron.setWeights(newWeights);
                }

                //atualizando pesos da camada oculta
                for (int x = 0; x < neuronsOcult.size(); x++) {
                    Neuron neuron = neuronsOcult.get(x);
                    ArrayList<Double> currentWeights = neuron.getWeights();

                    ArrayList<Double> newWeights = new ArrayList<>();
                    double newWeight;
                    for (int y = 0; y < currentWeights.size(); y++) {
                        newWeight = currentWeights.get(y)
                                + (learningRate * errorsOcultLayer.get(x)
                                * inputs.get(i).get(y));

                        newWeights.add(newWeight);
                    }

                    neuron.setWeights(newWeights);
                }
            }

            //calcular erro da rede
            double sumError = 0;

            for (int z = 0; z < errorsOutputLayer.size(); z++) {
                sumError += Math.pow(errorsOutputLayer.get(z), 2);
            }
            error = 0.5 * sumError;
            iterations++;
        }

    }

}
