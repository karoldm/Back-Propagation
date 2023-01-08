package com.mycompany.rn.project;

import java.util.ArrayList;

/**
 *
 * @author karol
 */
public class Layer {

    protected ArrayList<Neuron> neurons;
    protected ArrayList<Double> outputs;
    protected ArrayList<Double> errors;
    protected ArrayList<Double> inputs;
    protected int neuronsAmount;
    protected int function;

    public Layer(int neuronsAmount, int weightsAmount, int function) {
        this.errors = new ArrayList<>();
        this.function = function;
        this.neurons = new ArrayList<>();
        this.errors = new ArrayList<>();
        this.outputs = new ArrayList<>();

        this.neuronsAmount = neuronsAmount;

        //iniciando neuronios 
        //cada neuronio da camada possui weightsAmount pesos 
        //os pesos são escolhidos aleatóriamente entre -0.001 e 0.001
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

    public void setInputs(ArrayList<Double> inputs) {
        this.outputs = new ArrayList<>();
        for (int i = 0; i < this.neurons.size(); i++) {
            Neuron neuron = this.neurons.get(i);
            
            //inserindo entradas
            neuron.setInputs(inputs);
            
            //calculando saida de cada neuronio da camada oculta
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
