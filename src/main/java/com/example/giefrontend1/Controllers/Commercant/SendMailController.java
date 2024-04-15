package com.example.giefrontend1.Controllers.Commercant;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import okhttp3.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SendMailController implements Initializable {

    public TextField mail_textfield;
    public TextField sybject_txtfield;
    public TextArea message_txtfield;
    public Button SendEmail;
    public Label statut_lbl;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SendEmail.setOnAction(event -> sendEmail());
    }

    private void sendEmail() {
        String recipient = mail_textfield.getText();
        String subject = sybject_txtfield.getText();
        String body = message_txtfield.getText();

        // Create JSON payload
        String json = "{\"recipient\": \"" + recipient + "\", \"subject\": \"" + subject + "\", \"body\": \"" + body + "\"}";

        // Create OkHttpClient
        OkHttpClient client = new OkHttpClient();

        // Create RequestBody
        RequestBody requestBody = RequestBody.create(json, MediaType.parse("application/json"));

        // Create Request
        Request request = new Request.Builder()
                .url("http://localhost:4567/send-email")
                .post(requestBody)
                .build();

        // Send asynchronous request
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println("Response from server: " + response.body().string());
                Platform.runLater(() -> statut_lbl.setText("Email sent successfully!"));
                // You can handle the response here if needed
            }

            @Override
            public void onFailure(Call call, IOException e) {
                Platform.runLater(() -> statut_lbl.setText("An error occurred while sending the Email !"));
                e.printStackTrace();
            }
        });
    }


    public void onSendEmail(ActionEvent actionEvent) {
    }
}