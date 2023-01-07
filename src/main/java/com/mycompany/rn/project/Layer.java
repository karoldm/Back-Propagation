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
        
        this.neuronsAmount = neuronsAmount;

        //iniciando neuronios 
        //cada neuronio da camada possui weightsAmount pesos 
        //os pesos são escolhidos aleatóriamente entre -0.001 e 0.001
        ArrayList<Double> weights = new ArrayList<>();

        for (int i = 0; i < neuronsAmount; i++) {
            weights.clear();
            for (int j = 0; j < weightsAmount; j++) {
                double weight = Math.random() * (0.001 - (-0.001)) + (-0.001);
                weights.add(weight);
            }
            neurons.add(new Neuron(weights, function));
        }
    }

    public ArrayList<Neuron> getNeurons() {
        return neurons;
    }

    public void setNeurons(ArrayList<Neuron> neurons) {
        this.neurons = neurons;
    }

    public ArrayList<Double> getOutputs() {
        return outputs;
    }

    public void setOutputs(ArrayList<Double> outputs) {
        this.outputs = outputs;
    }

    public ArrayList<Double> getErrors() {
        return errors;
    }

    public void setErrors(ArrayList<Double> erros) {
        this.errors = erros;
    }

    public ArrayList<Double> getInputs() {
        return inputs;
    }

    public void setInputs(ArrayList<Double> inputs) {
        for (int j = 0; j < neurons.size(); j++) {
            Neuron neuron = neurons.get(j);
            //inserindo entradas
            neuron.setInputs(inputs);
            //calculando saida de cada neuronio da camada oculta
            outputs.add(neuron.getOutput());
        }
    }

    public int getNeuronsAmount() {
        return neuronsAmount;
    }

    public void setNeuronsAmount(int neuronsAmount) {
        this.neuronsAmount = neuronsAmount;
    }

}
