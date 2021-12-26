package netology.cloud.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record NewFileName(
        @JsonProperty("filename") String fileName){
}