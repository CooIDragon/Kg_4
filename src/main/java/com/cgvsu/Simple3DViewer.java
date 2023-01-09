package com.cgvsu;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Simple3DViewer extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        AnchorPane viewport = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/gui.fxml")));

        Scene scene = new Scene(viewport);

        stage.setMinWidth(1600);
        stage.setMinHeight(900);
        
        viewport.prefWidthProperty().bind(scene.widthProperty());
        viewport.prefHeightProperty().bind(scene.heightProperty());

        Text text = new Text();
        text.setX(300);
        text.setY((100));
        text.setText("- Я люблю спать, а спать любит меня, но утро не хочет, чтобы мы были вместе.\n");
        text.setFont(Font.font("Verdana", 25));
        text.setFill(Color.grayRgb(240));
        viewport.getChildren().add(text);

        stage.setTitle("Simple3DViewer");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}