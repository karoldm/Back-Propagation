package com.mycompany.rn.project;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author karol
 * Classe para leitura do arquivo CSV no formato: X1,X2,X3,...,Xn,CLASSE
 */
public class ReaderCSV {

    private static String linha = "";
    private static BufferedReader br = null;

    //Le o arquivo e retorna uma matriz de valores contendo as linhas e colunas do arquivo
    public static double[][] reader(String path) {
       double[][] matrixAttributes = new double[][];
        
        try {

            br = new BufferedReader(new FileReader(path));

            linha = br.readLine();
            
            int cont = 0;
            int contRow = 0;

            while ((linha = br.readLine()) != null) {

                String[] linhaSplit = linha.split(",");
                double[] row = new double[linhaSplit.length];

                for (String str : linhaSplit) {
                    row[contRow] = Double.parseDouble(str);
                    contRow++;
                }
                contRow = 0;
                matrixAttributes[cont] = row;
                cont++;
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

        return matrixAttributes;
    }

}
