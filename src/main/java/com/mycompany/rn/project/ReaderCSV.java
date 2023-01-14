package com.mycompany.rn.project;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author karol Classe para leitura do arquivo CSV no formato:
 * X1,X2,X3,...,Xn,CLASSE
 */
public class ReaderCSV {

    private static String linha = "";
    private static BufferedReader br = null;

    //Le o arquivo e retorna uma matriz de valores contendo as linhas e colunas do arquivo
    public static double[][] reader(String path) {
        ArrayList<ArrayList<Double>> matrixAttributes = new ArrayList<>();
        double[][] matrixAttributesArray = null;

        try {

            br = new BufferedReader(new FileReader(path));

            linha = br.readLine();

            while ((linha = br.readLine()) != null) {

                String[] linhaSplit = linha.split(",");
                ArrayList<Double> row = new ArrayList<>();

                for (String str : linhaSplit) {
                    row.add(Double.valueOf(str));
                }
                matrixAttributes.add(row);
            }

            matrixAttributesArray = new double[matrixAttributes.size()][matrixAttributes.get(0).size()];

            for(int i = 0; i < matrixAttributes.size(); i++){
                for(int j = 0; j < matrixAttributes.get(0).size(); j++){
                    matrixAttributesArray[i][j] = matrixAttributes.get(i).get(j);
                }
            }

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

        return matrixAttributesArray;
    }

}
