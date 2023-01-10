package com.mycompany.rn.project;

import java.util.ArrayList;

/**
 *
 * @author karol
 * Classe para representar uma camada de neuronios 
 * Toda camada possui um array de neuronios, as entradas e as saídas de cada neurônio
 * e os erros calculados de cada neuronio 
 */
public class Layer {

    protected ArrayList<Neuron> neurons; //array de neuronios
    protected ArrayList<Double> outputs; //array com as saídas calculadas de cada neuronio
    protected ArrayList<Double> errors; //array com os erros calculadas de cada neuronio
    protected ArrayList<Double> inputs; //array com as entradas de cada neuronio
    protected int neuronsAmount; //quantidade de neuronios
    protected int function; //função usada nos neuronios, onde 0 = logistica e 1 = tangente hiperbolica

    public Layer(int neuronsAmount, int weightsAmount, int function) {
        this.errors = new ArrayList<>();
        this.function = function;
        this.neurons = new ArrayList<>();
        this.errors = new ArrayList<>();
        this.outputs = new ArrayList<>();

        this.neuronsAmount = neuronsAmount;

        //iniciando neuronios 
        //cada neuronio da camada possui weightsAmount pesos 
        //os pesos são escolhidos aleatóriamente entre -1 e 1
        ArrayList<Double> weights = new ArrayList<>();

        for (int i = 0; i < neuronsAmount; i++) {
            weights = new ArrayList<>();
            
            for (int j = 0; j < weightsAmount; j++) {
                double weight = Math.random() * (1 - (-1)) + (-1);
                weights.add(weight);
            }
            
            Neuron newNeuron = new Neuron(weights, function);
            neurons.add(newNeuron);
        }
         
    }

    public ArrayList<Neuron> getNeurons() {
        return this.neurons;
    }

    public void setNeurons(ArrayList<Neuron> neurons) {
        this.neurons = neurons;
    }

    public ArrayList<Double> getOutputs() {
        return this.outputs;
    }

    public void setOutputs(ArrayList<Double> outputs) {
        this.outputs = outputs;
    }

    public ArrayList<Double> getErrors() {
        return this.errors;
    }

    public void setErrors(ArrayList<Double> erros) {
        this.errors = erros;
    }

    public ArrayList<Double> getInputs() {
        return this.inputs;
    }

    //O método setInputs recebe as entradas de cada neuronio, calcula os nets e aplica a função de transferencia
    //gerando uma saída para cada neuronio armazenadas no array outputs
    public void setInputs(ArrayList<Double> inputs) {
        this.outputs = new ArrayList<>();
        
        //para cada neuronio da camada 
        for (int i = 0; i < this.neurons.size(); i++) {
            Neuron neuron = this.neurons.get(i);
            
            //inserindo entradas
            neuron.setInputs(inputs);
            
            //calculando saida de cada neuronio da camada 
            this.outputs.add(neuron.getOutput());
        }
    }

    public int getNeuronsAmount() {
        return this.neuronsAmount;
    }

    public void setNeuronsAmount(int neuronsAmount) {
        this.neuronsAmount = neuronsAmount;
    }

}
