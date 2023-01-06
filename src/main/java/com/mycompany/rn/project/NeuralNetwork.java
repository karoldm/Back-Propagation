
package com.mycompany.rn.project;

import java.util.ArrayList;

/**
 *
 * @author karol
 */
public class NeuralNetwork {
       private int classAmount; 
       private int attributesAmount;
       private ArrayList<ArrayList<Integer>> matrixAttributes;

    public NeuralNetwork() {
        matrixAttributes = new ArrayList<>();
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
    
    public void addAttributeRow(ArrayList<Integer> row){
        matrixAttributes.add(row);
    }
    
    public void calculateClassAmount(){
        int classAmount = 0;
        
        int sizeMatrix = matrixAttributes.size();
        
        ArrayList<Integer> row = matrixAttributes.get(sizeMatrix-1);
        
        int sizeRow = row.size();
        
        int class1 = row.get(sizeRow-1);
        
        for(ArrayList<Integer> rowInterator: matrixAttributes){
            if(class1 != rowInterator.get(sizeRow-1)) classAmount++;
            class1 = rowInterator.get(sizeRow-1);
        }
        
        setClassAmount(classAmount);
    }
       
       
}
