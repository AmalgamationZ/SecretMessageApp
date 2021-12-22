package main;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;


public class App extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    private void decodeFile(Stage stage) {
        try {
            // choose file to decode
            FileChooser chooser = new FileChooser();
            FileChooser.ExtensionFilter extFilter =
                    new FileChooser.ExtensionFilter("TXT files (*"
                            + ".txt)", "*.txt");
            chooser.getExtensionFilters().add(extFilter);

            File encodedFile = chooser.showOpenDialog(stage);

            // if no file is selected (i.e. the user
            // cancels), then the program will simply
            // return home
            if (encodedFile == null) {
                return;
            }

            // reads file as UTF8 file
            FileInputStream stream = new FileInputStream(encodedFile);
            InputStreamReader reader = new InputStreamReader(stream,
                    StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(reader);
            String data = br.lines().collect(Collectors.joining());

            // closes input streams
            br.close();
            reader.close();
            stream.close();

            // encodes message
            String decoded = Conversion.decodeMessage(data);

            showAlert("The program will now allow you" +
                    " to save your encoded or decoded file.");

            //Show save file dialog
            chooser.setInitialFileName("*.txt");
            File file = chooser.showSaveDialog(stage);

            // saves decoded text to text file
            if (file != null) {
                PrintWriter writer = new PrintWriter(file);
                writer.print(decoded);
                writer.close();
            }

        } catch (Exception e) {
            showAlert("Unknown error, exiting...");
        }
    }

    private void encodeFile(Stage stage) {
        try {
            // choose file to encode
            FileChooser chooser = new FileChooser();
            File decodedFile = chooser.showOpenDialog(stage);

            // if no file is selected (i.e. the user
            // cancels), then the program will simply
            // return home
            if (decodedFile == null) {
                return;
            }

            // reads file as UTF8 file
            FileInputStream stream = new FileInputStream(decodedFile);
            InputStreamReader reader = new InputStreamReader(stream,
                    StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(reader);
            String data = br.lines().collect(Collectors.joining());

            // closes input streams
            br.close();
            reader.close();
            stream.close();


            // encodes message
            String encoded = Conversion.encodeMessage(data);

            showAlert("The program will now allow you" +
                    " to save your encoded or decoded file.");

            // show save file dialog
            chooser.setInitialFileName("*.txt");
            File file = chooser.showSaveDialog(stage);

            // saves encoded text to text file
            if (file != null) {
                PrintWriter writer = new PrintWriter(file);
                writer.print(encoded);
                writer.close();
            }
        } catch (Exception e) {
            showAlert("File not chosen or cancelled operation.");
        }
    }

    private void showAlert(String message) {
        // text area to display message in
        TextArea textArea = new TextArea(message);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        // Container for text area
        GridPane gridPane = new GridPane();
        gridPane.setMaxWidth(Double.MAX_VALUE / 2);
        gridPane.add(textArea, 0, 0);

        // creates alert for viewer
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Notification");
        alert.getDialogPane().setContent(gridPane);
        alert.showAndWait();
    }

    private void showDecodedMessage(TextArea message) {
        // original message
        String original = message.getText();

        // doesn't show message dialog if
        // input is invalid
        if (!validateTextArea(original)) {
            return;
        }

        // encoded message
        String decoded = Conversion.decodeMessage(original);

        showAlert(decoded);
    }

    private boolean validateTextArea(String message) {
        // checks if text area only has white space
        // or is empty
        if (message.isEmpty() || message.isBlank()) {
            showAlert("Please ensure you typed in alphanumeric " +
                    "characters (for encoding)" +
                    " or 0's/1's (for decoding)" +
                    " in the text box!");
            return false;
        }

        return true;
    }

    private void showEncodedMessage(TextArea message) {
        // original message
        String original = message.getText();

        // doesn't show message dialog if
        // input is invalid
        if (!validateTextArea(original)) {
            return;
        }

        // encoded message
        String encoded = Conversion.encodeMessage(original);

        showAlert(encoded);
    }

    @Override
    public void start(Stage stage) {
        // welcome and instructions text
        Text instructions = new Text("Welcome, operative! " +
                "To encode a message," + " enter the message you " +
                "wish to encode and " + "press the \"Encode\" " +
                "Button.\n\n" + "To decode a message, enter the " +
                "encoded " + "message you wish to decode and press " +
                "the \"Decode\" Button." + "\n\nYou can also press " +
                "the \"Encode File\" button to encode an " +
                "existing .txt file, or press the" +
                " \"Decode File\" button to decode an existing " +
                ".txt file.");

        // text area to put encoded or decoded message inside
        TextArea message = new TextArea();
        message.setWrapText(true);
        message.setPromptText("Insert encoded or " +
                "decoded message here.");

        // encoding message button
        Button encode = new Button("Encode");
        encode.setOnAction(e -> showEncodedMessage(message));

        // decoding message button
        Button decode = new Button("Decode");
        decode.setOnAction(e -> showDecodedMessage(message));

        // get file of encoded message button
        Button getEncoded = new Button("Encode File");
        getEncoded.setOnAction(e -> encodeFile(stage));
        Button getDecoded = new Button("Decoded File");
        getDecoded.setOnAction(e -> decodeFile(stage));

        // overall vBox layout
        VBox vBox = new VBox();

        // HBox for encode and decode buttons
        HBox hBox1 = new HBox();
        hBox1.getChildren().addAll(encode, decode);
        hBox1.setSpacing(10);
        hBox1.setPadding(new Insets(10, 10, 10, 10));
        hBox1.setAlignment(Pos.CENTER);

        // HBox for encode file and decode file buttons
        HBox hBox2 = new HBox();
        hBox2.getChildren().addAll(getEncoded, getDecoded);
        hBox2.setSpacing(10);
        hBox2.setPadding(new Insets(10, 10, 10, 10));
        hBox2.setAlignment(Pos.CENTER);

        // adds appropriate items
        vBox.getChildren().addAll(instructions, message, hBox1,
                hBox2);
        vBox.setPadding(new Insets(10, 10, 10, 10));
        vBox.setSpacing(10);

        vBox.setStyle("-fx-font-size: 16px;\n" +
                "    -fx-font-family: Courier New;\n" +
                "    -fx-alignment: center;\n");

        Scene scene = new Scene(vBox);


        stage.setTitle("\"Super Secret\" App");
        stage.setScene(scene);
        stage.show();
    }

}