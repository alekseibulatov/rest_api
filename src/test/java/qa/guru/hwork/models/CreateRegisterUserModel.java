package qa.guru.hwork.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateRegisterUserModel {

    private String email;

    private String password;
}
