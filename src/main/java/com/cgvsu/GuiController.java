package com.cgvsu;

import com.cgvsu.objreader.ObjReaderException;
import com.cgvsu.render_engine.RenderEngine;
import com.cgvsu.writer.ObjWriter;
import javafx.fxml.FXML;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;
import java.io.File;
import javax.vecmath.Vector3f;

import com.cgvsu.model.Model;
import com.cgvsu.objreader.ObjReader;
import com.cgvsu.render_engine.Camera;

public class GuiController {

    final private float TRANSLATION = 0.5F;
    public Button butt1;
    public Button butt2;

    @FXML
    AnchorPane anchorPane;

    @FXML
    private Canvas canvas1;
    @FXML
    private Canvas canvas2;

    private boolean switchCam = false;

    private Model mesh1 = null;
    private Model mesh2 = null;

    private Camera camera1 = new Camera(
            new Vector3f(0, 00, 100),
            new Vector3f(0, 0, 0),
            1.0F, 1, 0.01F, 100);

    private Camera camera2 = new Camera(
            new Vector3f(0, 00, 100),
            new Vector3f(0, 0, 0),
            1.0F, 1, 0.01F, 100);

    private Timeline timeline;

    @FXML
    private void initialize() {
        anchorPane.prefWidthProperty().addListener((ov, oldValue, newValue) -> canvas1.setWidth(newValue.doubleValue()));
        anchorPane.prefHeightProperty().addListener((ov, oldValue, newValue) -> canvas1.setHeight(newValue.doubleValue()));

        anchorPane.prefWidthProperty().addListener((ov, oldValue, newValue) -> canvas2.setWidth(newValue.doubleValue()));
        anchorPane.prefHeightProperty().addListener((ov, oldValue, newValue) -> canvas2.setHeight(newValue.doubleValue()));

        timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);

        KeyFrame frame = new KeyFrame(Duration.millis(15), event -> {
            double width = canvas1.getWidth();
            double height = canvas1.getHeight();

            canvas1.getGraphicsContext2D().clearRect(0, 0, width, height);
            canvas2.getGraphicsContext2D().clearRect(0, 0, width, height);

            camera1.setAspectRatio((float) (width / height));
            camera2.setAspectRatio((float) (width / height));

            if (mesh1 != null) {
                RenderEngine.render(canvas1.getGraphicsContext2D(), camera1, mesh1, (int) width, (int) height);
            }

            if (mesh2 != null) {
                RenderEngine.render(canvas2.getGraphicsContext2D(), camera2, mesh2, (int) width, (int) height);
            }
        });

        timeline.getKeyFrames().add(frame);
        timeline.play();
    }

    @FXML
    private void onOpenModel1MenuItemClick() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Model (*.obj)", "*.obj"));
        fileChooser.setTitle("Load Model");

        File file = fileChooser.showOpenDialog((Stage) canvas1.getScene().getWindow());
        if (file == null) {
            return;
        }

        Path fileName = Path.of(file.getAbsolutePath());

        try {
            String fileContent = Files.readString(fileName);
            mesh1 = ObjReader.read(fileContent);
        } catch (ObjReaderException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);

            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());

            alert.showAndWait();
        }
    }

    @FXML
    private void onOpenModel2MenuItemClick() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Model (*.obj)", "*.obj"));
        fileChooser.setTitle("Load Model");

        File file = fileChooser.showOpenDialog((Stage) canvas2.getScene().getWindow());
        if (file == null) {
            return;
        }

        Path fileName = Path.of(file.getAbsolutePath());

        try {
            String fileContent = Files.readString(fileName);
            mesh2 = ObjReader.read(fileContent);
        } catch (ObjReaderException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);

            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());

            alert.showAndWait();
        }
    }

    @FXML
    public void handleCamera1Forward(ActionEvent actionEvent) {
        if (!switchCam) {
            camera1.movePosition(new Vector3f(0, 0, -TRANSLATION));
        }
    }

    @FXML
    public void handleCamera1Backward(ActionEvent actionEvent) {
        if (!switchCam) {
            camera1.movePosition(new Vector3f(0, 0, TRANSLATION));
        }
    }

    @FXML
    public void handleCamera1Left(ActionEvent actionEvent) {
        if (!switchCam) {
            camera1.movePosition(new Vector3f(TRANSLATION, 0, 0));
        }
    }

    @FXML
    public void handleCamera1Right(ActionEvent actionEvent) {
        if (!switchCam) {
            camera1.movePosition(new Vector3f(-TRANSLATION, 0, 0));
        }
    }

    @FXML
    public void handleCamera1Up(ActionEvent actionEvent) {
        if (!switchCam) {
            camera1.movePosition(new Vector3f(0, TRANSLATION, 0));
        }
    }

    @FXML
    public void handleCamera1Down(ActionEvent actionEvent) {
        if (!switchCam) {
            camera1.movePosition(new Vector3f(0, -TRANSLATION, 0));
        }
    }

    @FXML
    public void handleCamera2Forward(ActionEvent actionEvent) {
        if (switchCam) {
            camera2.movePosition(new Vector3f(0, 0, -TRANSLATION));
        }
    }

    @FXML
    public void handleCamera2Backward(ActionEvent actionEvent) {
        if (switchCam) {
            camera2.movePosition(new Vector3f(0, 0, TRANSLATION));
        }
    }

    @FXML
    public void handleCamera2Left(ActionEvent actionEvent) {
        if (switchCam) {
            camera2.movePosition(new Vector3f(TRANSLATION, 0, 0));
        }
    }

    @FXML
    public void handleCamera2Right(ActionEvent actionEvent) {
        if (switchCam) {
            camera2.movePosition(new Vector3f(-TRANSLATION, 0, 0));
        }
    }

    @FXML
    public void handleCamera2Up(ActionEvent actionEvent) {
        if (switchCam) {
            camera2.movePosition(new Vector3f(0, TRANSLATION, 0));
        }
    }

    @FXML
    public void handleCamera2Down(ActionEvent actionEvent) {
        if (switchCam) {
            camera2.movePosition(new Vector3f(0, -TRANSLATION, 0));
        }
    }

    public void onUploadModel1MenuItemClick(ActionEvent actionEvent) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Model (*.obj)", "*.obj"));
        fileChooser.setTitle("Save Model");

        File file = fileChooser.showOpenDialog((Stage) canvas1.getScene().getWindow());
        if (file == null) {
            return;
        }

        ObjWriter.writeToFile(mesh1, file);
    }

    public void onUploadModel2MenuItemClick(ActionEvent actionEvent) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Model (*.obj)", "*.obj"));
        fileChooser.setTitle("Save Model");

        File file = fileChooser.showOpenDialog((Stage) canvas2.getScene().getWindow());
        if (file == null) {
            return;
        }

        ObjWriter.writeToFile(mesh2, file);
    }

    public void butt1Click(ActionEvent actionEvent) {
        switchCam = false;
    }

    public void butt2Click(ActionEvent actionEvent) {
        switchCam = true;
    }
}