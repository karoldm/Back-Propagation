package com.mycompany.rn.project;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author karol
 */
public class ReaderCSV {

    private static String linha = "";
    private static BufferedReader br = null;

    public static NeuralNetwork reader(String path) {
        int classAmount = 0, attributesAmount = 0;
        NeuralNetwork NN = new NeuralNetwork();

        try {

            br = new BufferedReader(new FileReader(path));

            linha = br.readLine();
            attributesAmount = linha.split(",").length - 1;
            NN.setAttributesAmount(attributesAmount);

            while ((linha = br.readLine()) != null) {

                String[] linhaSplit = linha.split(",");
                ArrayList<Integer> row = new ArrayList<>();

                for (String str : linhaSplit) {
                    row.add(Integer.parseInt(str));
                }

                NN.addAttributeRow(row);
            }
            
            NN.calculateClassAmount();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return NN;
    }

}
