package com.example.dbtry1;
import com.example.dbtry1.db.DBQuery;
import com.example.dbtry1.db.DatabaseException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.sql.SQLException;

import static com.example.dbtry1.SceneController.switchToMenu;
import static com.example.dbtry1.other.Values.*;

public class EditProfileController {
    @FXML
    private TextField ep_user_field;

    @FXML
    private TextField ep_pass_field;

    @FXML
    private Label lbl_message;

    public String message = "";
    public String newUsername = "";
    public String newPassword = "";

    /*
         wrong username while changing password
     */
    public boolean isUsernameValid() throws DatabaseException {
        newUsername = ep_user_field.getText();
        newPassword = ep_pass_field.getText();

        if (!newUsername.equals(LoginController.username)) {
            message = INVALID_USERNAME;
            showMessage();
            return false;
        }

        return true;
    }

    /*
        wrong password while changing username
    */
    public boolean isPasswordValid() {
        newUsername = ep_user_field.getText();
        newPassword = ep_pass_field.getText();

        if (!newPassword.equals(LoginController.password)) {
            message = INVALID_PASSWORD;
            showMessage();
            return false;
        }
        if (newPassword.length() < 7) {
            message = PASSWORD_8_CHARACTER;
            showMessage();
            return false;
        }
        return true;
    }

    public void changePassword(ActionEvent event) throws SQLException {
        if(isUsernameValid()){
            DBQuery queryHandler = new DBQuery();
            try {

                queryHandler.changePassword(LoginController.username, newPassword,LoginController.password);
                LoginController.password = newPassword;
                message = PASSWORD_CHANGED;

                showMessage();

            } catch (DatabaseException e) {
                message = e.getMessage();
                showMessage();
            }
        }
    }
    public void changeUsername() throws DatabaseException {
        if (isPasswordValid()){
            DBQuery queryHandler = new DBQuery();
            try {

                queryHandler.changeUsername(newUsername,LoginController.username, LoginController.password);
                LoginController.username = newUsername;
                message = USERNAME_CHANGED;

                showMessage();

            } catch (DatabaseException e) {
                message = e.getMessage();
                showMessage();
            }
        }
    }

    public void showMessage() {
        lbl_message.setText(message);
        System.out.println(message);
    }

    public void goToMenu(){
        switchToMenu();
    }

}
