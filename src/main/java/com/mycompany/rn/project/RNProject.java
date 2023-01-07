

package com.mycompany.rn.project;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

/**
 *
 * @author karol
 */
public class RNProject {

    public static void main(String[] args) {
        
        JFrame form = new Form();
        form.setName("Rede Neural");
        form.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        form.setVisible(true);
    }
}