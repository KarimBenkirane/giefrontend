package com.example.giefrontend1;

import com.example.giefrontend1.Models.Model;
import javafx.application.Application;

import javafx.stage.Stage;

public class Run extends Application {
    @Override
    public void start(Stage stage) {
        Model.getInstance().getViewFactory().showAdminWindow();
    }
    public static void main(String[] args) {
        launch();
    }
}
