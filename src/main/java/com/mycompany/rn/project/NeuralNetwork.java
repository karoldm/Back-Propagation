package com.mycompany.rn.project;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author karol
 * Classe que representa a rede neural
 */
public class NeuralNetwork {

    private int classAmount; //quantidade de classes na rede ou quantidade de neuronios na camada de saída
    private int attributesAmount; //quantidade de atributos ou quantidade de neuronios na camada de entrada
    private int neuronsOcultLayerAmount; //quantidade de neuronios na camada oculta 
    private final ArrayList<ArrayList<Double>> matrixAttributes; //matriz com os atributos e classe do conjunto de treinamento
    //formato: X1 X2 X3 X4 X5 ... Xn CLASSE
    private int function; //0 = logistic and 1 = hyperbolic tangent
    private int stop; //0 = max error and 1 = number of iterations
    private double stopNumber; //condição de parada segundo número máximo de iterações ou erro máximo
    private double learningRate; //taxa de aprendizado
    private OcultLayer ocultLayer; //classe com responsavel pelos neuronios da camada oculta
    private OutputLayer outputLayer; //classe com responsavel pelos neuronios da camada de saída

    //Ao criar a NN passamos o conjunto de atributosXclasse lido pelo CSV
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

        //contando quantidade de atributos 
        this.attributesAmount = matrixAttributes.get(0).size() - 1;

        int sum = 0;

        int sizeMatrix = matrixAttributes.size();

        ArrayList<Double> row = matrixAttributes.get(sizeMatrix - 1);

        int sizeRow = row.size();

        Set<Double> classes = new HashSet();

        //Contando quantidade de classes
        for (ArrayList<Double> rowIterator : matrixAttributes) {
            classes.add(rowIterator.get(sizeRow - 1));
        }

        this.classAmount = classes.size();
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

    //Método para treinar a rede com o conjunto matrixAttributes
    public void initTraining() {
        
        //iniciando neuronios da camada oculta
        this.ocultLayer = new OcultLayer(neuronsOcultLayerAmount, attributesAmount, function);

        //iniciando neuronios da camada de saída
        this.outputLayer = new OutputLayer(classAmount, neuronsOcultLayerAmount, function);

        //Separando dados de entrada de suas classes onde 
        ////inputs[i] contém X1 X2 X3 X4 ... e classeDesejada[i] contém CLASSE
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

        double error = 10000;
        int iterations = 0;
        while (getStopCondiction(error, iterations)) {

            //para cada linha de atributos do conjunto de treinamento
            for (int i = 0; i < inputs.size(); i++) {

                //calculando saidas dos neuronios da camada oculta 
                //o método setInputs insere os dados da linha i em cada neuronio da camada oculta 
                //calcula seus nets e aplica a função de transferencia, armazenando o resultado no
                //array outputs
                ocultLayer.setInputs(inputs.get(i));

                //calculando saidas dos neuronios da camada de saída 
                //o método setInputs insere as saídas dos neuronios da camada oculta em
                //cada neuronio da camada de saída calcula seus nets e aplica a função 
                //de transferencia, armazenando o resultado no array outputs
                outputLayer.setInputs(ocultLayer.getOutputs());

                //Calculando erro dos neuronios da camada de saída 
                //o método caclErrors calcula o erro de cada neuronio da camada de saída 
                //e os armazena no array errors
                outputLayer.calcErrors(classeDesejada.get(i));
                
                //Calculando erro dos neuronios da camada oculta
                //o método caclErrors calcula o erro de cada neuronio da camada oculta
                //e os armazena no array errors
                ocultLayer.calcErrors(outputLayer.getNeurons(), outputLayer.getErrors());
                
                //Atualizando pesos dos neuronios da camada de saída
                //o método updateWeights atualiza os pesos de cada neuronio
                outputLayer.updateWeights(learningRate, ocultLayer.getOutputs());

                //Atualizando pesos dos neuronios da camada oculta
                //o método updateWeights atualiza os pesos de cada neuronio
                ocultLayer.updateWeights(learningRate, inputs.get(i));
            }

            //calcular erro da rede
            error = outputLayer.getNetworkError();
            //incrementando iterações
            iterations++;
        }

    }

    //Método para testar a rede com um conjunto de entradas
    public int[][] test(ArrayList<ArrayList<Double>> matrixAttributesTest) {

        ArrayList<Integer> classeDesejada = new ArrayList<>();
        ArrayList<ArrayList<Double>> inputs = new ArrayList<>();

        int[][] confusionMatrix = new int[classAmount][classAmount];

        //inicializando matriz de confusão
        for (int i = 0; i < classAmount; i++) {
            for (int j = 0; j < classAmount; j++) {
                confusionMatrix[i][j] = 0;
            }
        }

        //separando atributos de suas classes, de modo que 
        //inputs[i] contém X1 X2 X3 X4 ... e classeDesejada[i] contém CLASSE
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
            
            //A classe é definida pelo neuronio que possuir a maior saída
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
