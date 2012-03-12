/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javafxplatform.core.startup;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

/**
 *
 * @author puce
 */
public class ContentPane extends BorderPane {

    private final ContentPane.Controller controller;

    public ContentPane() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setClassLoader(ContentPane.class.getClassLoader());
        loader.setResources(ResourceBundle.getBundle("org.javafxplatform.core.startup.Bundle"));
        Pane root = (Pane) loader.load(ContentPane.class.getResourceAsStream("ContentPane.fxml"));
        controller = (ContentPane.Controller) loader.getController();
        setCenter(root);
    }


    public static class Controller implements Initializable {

        @FXML
        private MenuBar menuBar;

        @Override
        public void initialize(URL url, ResourceBundle rb) {
        }
    }
}