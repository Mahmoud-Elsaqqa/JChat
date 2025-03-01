package Client.ui.controllers;

import Client.Hashing.Hashing;
import Client.network.RMIClientServices;
import Client.ui.models.CurrentUserAccount;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import exceptions.DuplicateUserException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.user.UserDto;
import model.user.UserStatus;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

public class SignUp3Controller implements Initializable {

    @FXML
    private AnchorPane signInPane;

    @FXML
    private JFXTextField userBio;

    @FXML
    private JFXTextField displayNameField;

    @FXML
    private Button confirmCreateAccountBtn;

    @FXML
    private Circle userAvatar;

    private Image userImage;

    @FXML
    void handleConfirmCreateAccount(MouseEvent event) throws DuplicateUserException, IOException, NoSuchAlgorithmException {
        if (displayNameField.validate()) {
            CurrentUserAccount populatedUserData = CurrentUserAccount.getInstance();
            populatedUserData.setName(displayNameField.getText());
            populatedUserData.setBio(userBio.getText());
            populatedUserData.setPicture(userImage);
            populatedUserData.setStatus(UserStatus.AVAILABLE);

            //Create A DTO and Send to Server
            UserDto newCreatedUser = new UserDto();
            newCreatedUser.setMobile(populatedUserData.getMobile());
            newCreatedUser.setPassword(Hashing.hashPass(populatedUserData.getPassword()));
            newCreatedUser.setEmail(populatedUserData.getEmail());
            newCreatedUser.setGender(populatedUserData.getGender());
            newCreatedUser.setCountry(populatedUserData.getCountry());
            newCreatedUser.setDateOfBirth(populatedUserData.getDateOfBirth());
            newCreatedUser.setBio(populatedUserData.getBio());
            newCreatedUser.setName(populatedUserData.getName());
            newCreatedUser.setPicture((populatedUserData.getPictureAsBytes()));

            RMIClientServices.signUp(newCreatedUser);


            //Switch to Main Screen
            Scene home = null;
            try {
                MainController mainController = MainController.getInstance();
                FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("/FXML/main.fxml"));
                mainLoader.setController(mainController);
                Parent mainPane = mainLoader.load();
                home = new Scene(mainPane);

                Node node = (Node) event.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                Stage homeStage = new Stage();
                homeStage.setScene(home);
                homeStage.setResizable(true);
                homeStage.show();
                stage.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    void openFileChooser(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();

        //Set extension filter
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
        fileChooser.getExtensionFilters().addAll(extFilterPNG);

        //Show open file dialog
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            userImage = new Image(file.toURI().toString());
            userAvatar.setFill(new ImagePattern(userImage));
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //validating required displayname field
        RequiredFieldValidator requiredNameField = new RequiredFieldValidator();
        requiredNameField.setMessage("Please Enter a display Name");
        displayNameField.getValidators().add(requiredNameField);

        //initializing default bio and image
        userBio.setText("Hello, I'm using JChat!");
        userImage = new Image(getClass().getResourceAsStream("/images/image-placeholder.png"));
        userAvatar.setFill(new ImagePattern(userImage));


    }
}
