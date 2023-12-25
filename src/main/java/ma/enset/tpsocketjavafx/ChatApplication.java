package ma.enset.tpsocketjavafx;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;

public class ChatApplication extends Application {
    static PrintWriter pw;
    static BufferedReader br;

    @Override
    public void start(Stage stage) {
        BorderPane root = new BorderPane();
        Label labelMessage = new Label("Message: ");
        TextField textField = new TextField();
        Label labelDest = new Label("Dest: ");
        TextField textFieldDest = new TextField();
        Button buttonSend = new Button("Send");
        HBox hBox = new HBox();
        hBox.setSpacing(20);
        hBox.getChildren().addAll(labelMessage, textField, labelDest, textFieldDest, buttonSend);
        root.setBottom(hBox);

        ObservableList<String> msgData = FXCollections.observableArrayList();
        ListView<String> listView = new ListView<>(msgData);
        listView.setPadding(new Insets(20));
        VBox vBox = new VBox();
        vBox.getChildren().add(listView);
        vBox.setPadding(new Insets(10));
        root.setCenter(vBox);

        Scene scene = new Scene(root, 600, 400);
        stage.setScene(scene);
        stage.show();

        buttonSend.setOnAction(actionEvent -> {
            String msg = textField.getText() + "=>" + textFieldDest.getText();
            pw.println(msg);
            msgData.add("moi: " + textField.getText());
            textField.clear();
            textFieldDest.clear();
        });
        new Thread(() -> {
            while (true) {
                try {
                    String msg = br.readLine();
                    Platform.runLater(()->msgData.add(msg));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 8080)){
            OutputStream os = socket.getOutputStream();
            pw = new PrintWriter(new OutputStreamWriter(os), true);

            InputStream is = socket.getInputStream();
            br = new BufferedReader(new InputStreamReader(is));
            launch();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}