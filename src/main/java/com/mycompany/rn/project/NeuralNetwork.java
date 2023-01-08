package com.mycompany.rn.project;

import java.util.ArrayList;

/**
 *
 * @author karol
 */
public class NeuralNetwork {

    private int classAmount;
    private int attributesAmount;
    private int neuronsOcultLayerAmount;
    private final ArrayList<ArrayList<Double>> matrixAttributes;
    private int function; //0 = logistic and 1 = hyperbolic tangent
    private int stop; //0 = max error and 1 = number of iterations
    private double stopNumber;
    private double learningRate;
    private OcultLayer ocultLayer;
    private OutputLayer outputLayer;

    public NeuralNetwork(ArrayList<ArrayList<Double>> matrixAttributes) {
        this.matrixAttributes = matrixAttributes;
        this.classAmount = 0;
        this.neuronsOcultLayerAmount = 0;
        this.function = 0;
        this.stop = 0;
        this.stopNumber = 0;
        this.learningRate = 1;
        this.ocultLayer = null;
        this.outputLayer = null;

        this.attributesAmount = matrixAttributes.get(0).size() - 1;

        int sum = 0;

        int sizeMatrix = matrixAttributes.size();

        ArrayList<Double> row = matrixAttributes.get(sizeMatrix - 1);

        int sizeRow = row.size();

        double classe = row.get(sizeRow - 1);

        for (ArrayList<Double> rowInterator : matrixAttributes) {
            if (classe != rowInterator.get(sizeRow - 1)) {
                sum++;
            }
            classe = rowInterator.get(sizeRow - 1);
        }

        setClassAmount(sum);
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

    public int getNeuronsOcultLayerAmount() {
        return neuronsOcultLayerAmount;
    }

    public void setNeuronsOcultLayerAmount(int neuronsOcultLayerAmount) {
        this.neuronsOcultLayerAmount = neuronsOcultLayerAmount;
    }

    public int calculateNeuronsOcultLayer() {
        return (int) Math.round(Math.sqrt(classAmount * attributesAmount));
    }

    private boolean getStopCondiction(double error, int iterations) {
        if (stop == 0 && error < stopNumber) { //maxError
            return false;
        }
        if (stop == 1 && iterations >= stopNumber) {
            return false;
        }
        return true;
    }

    public void initTraining() {
        //iniciando neuronios da camada oculta
        this.ocultLayer = new OcultLayer(neuronsOcultLayerAmount, attributesAmount, function);

        //iniciando neuronios da camada de saída
        this.outputLayer = new OutputLayer(classAmount, neuronsOcultLayerAmount, function);

        //Separando entrada de dados
        ArrayList<Double> classeDesejada = new ArrayList<>();
        ArrayList<ArrayList<Double>> inputs = new ArrayList<>();

        for (int i = 0; i < matrixAttributes.size(); i++) {

            inputs.add(new ArrayList<>());

            //Selecionando a classe desejada 
            ArrayList<Double> row = matrixAttributes.get(i);
            classeDesejada.add(row.get(attributesAmount));

            for (int j = 0; j < row.size() - 1; j++) {
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
                outputLayer.setInputs(ocultLayer.getOutputs());

                //Calculando erro dos neuronios da camada de saída
                outputLayer.calcErrors(classeDesejada.get(i));
                
                //Calculando erro dos neuronios da camada oculta
                ocultLayer.calcErrors(outputLayer.getNeurons(), outputLayer.getErrors());
                
                //calculando erros e atualizando pesos dos neuronios da camada de saída
                outputLayer.updateWeights(learningRate, ocultLayer.getOutputs());

                //calculando erros e atualizando pesos dos neuronios da camada oculta
                ocultLayer.updateWeights(learningRate, inputs.get(i));
            }

            //calcular erro da rede
            error = outputLayer.getNetworkError();
            //incrementando iterações
            iterations++;
        }

    }

    public int[][] test(ArrayList<ArrayList<Double>> matrixAttributesTest) {

        //Separando entrada de dados
        ArrayList<Integer> classeDesejada = new ArrayList<>();
        ArrayList<ArrayList<Double>> inputs = new ArrayList<>();

        int[][] confusionMatrix = new int[classAmount][classAmount];

        //inicializando matriz de confusão
        for (int i = 0; i < classAmount; i++) {
            for (int j = 0; j < classAmount; j++) {
                confusionMatrix[i][j] = 0;
            }
        }

        for (int i = 0; i < matrixAttributesTest.size(); i++) {

            inputs.add(new ArrayList<>());

            //Selecionando a classe desejada 
            ArrayList<Double> row = matrixAttributesTest.get(i);
            classeDesejada.add((int) Math.round(row.get(attributesAmount)));

            for (int j = 0; j < row.size() - 1; j++) {
                inputs.get(i).add((double) row.get(j));
            }
        }

        //inputs[i] contém X1 X2 X3 X4 ... e classeDesejada[i] contém CLASSE
        //para cada linha de atributos
        for (int i = 0; i < inputs.size(); i++) {

            //calculando saidas dos neuronios da camada oculta 
            ocultLayer.setInputs(inputs.get(i));
      
            //calculando saidas dos neuronios da camada de saída 
            outputLayer.setInputs(ocultLayer.getOutputs());
            
            //Se outputs = [0.3 0.8 0.1 -0.1 0.05 0.5] classe = 2
            ArrayList<Double> outputs = outputLayer.getOutputs();
            
            double maxValue = outputs.get(0);
            int maxIndex = 0;
            for (int j = 0; j < outputs.size(); j++) {
                if(outputs.get(j).isNaN()) System.out.println("Linha " + inputs.get(i));
                if (outputs.get(j) > maxValue) {
                    maxValue = outputs.get(j);
                    maxIndex = j;
                }
            }
            confusionMatrix[maxIndex][classeDesejada.get(i)-1] += 1;
        }

        return confusionMatrix;
    }

}
