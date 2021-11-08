package util;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.scene.control.TextField;

import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class ValidationUtil {

    public static Object validationCustomer(LinkedHashMap<TextField, Pattern> map, JFXButton btnChange) {
        btnChange.setDisable(true);
        for (TextField textFieldKey : map.keySet()) {
            Pattern patternValue = map.get(textFieldKey);
            if (!patternValue.matcher(textFieldKey.getText()).matches()) {
                if (!textFieldKey.getText().isEmpty()){
                    textFieldKey.setStyle("-fx-text-fill: #ff7979");

                }
                return textFieldKey;
            }
            textFieldKey.setStyle("-fx-text-fill:  #78e08f");

        }

        return true;
    }

    public static Object validation(LinkedHashMap<TextField, Pattern> map, JFXButton btnChange) {
        btnChange.setDisable(true);
        for (TextField textFieldKey : map.keySet()) {
            Pattern patternValue = map.get(textFieldKey);
            if (!patternValue.matcher(textFieldKey.getText()).matches()) {
                if (!textFieldKey.getText().isEmpty()){
                    textFieldKey.setStyle("-fx-text-fill: #ff7979");

                }
                return textFieldKey;
            }
            textFieldKey.setStyle("-fx-text-fill:  #78e08f");
        }
        btnChange.setDisable(false);
        return true;
    }

    public static Object validationCustomerRegister(LinkedHashMap<JFXTextField, Pattern> map, JFXButton btnConfirm) {

        btnConfirm.setDisable(true);
        for (JFXTextField textFieldKey : map.keySet()) {
            Pattern patternValue = map.get(textFieldKey);
            if (!patternValue.matcher(textFieldKey.getText()).matches()) {
                if (!textFieldKey.getText().isEmpty()){
                    textFieldKey.setStyle("-fx-text-fill: #EA2027");

                }
                return textFieldKey;
            }
            textFieldKey.setStyle("-fx-text-fill:  black");

        }
        btnConfirm.setDisable(false);
        return true;
    }
}
