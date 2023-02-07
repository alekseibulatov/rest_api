package qa.guru.hwork.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ListUsersModel {

    private Integer page, total;

    @JsonProperty("per_page")
    private String perPage;

    @JsonProperty("total_pages")
    private String totalPages;

   ArrayList<UserModel> data;
}
