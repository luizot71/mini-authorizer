package br.com.api.vr.benefits.miniauthorizer.model.errors;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor

public class BenefitsCardErrorModel {

    @NonNull
    @Schema(name = "Http status code", example = "400", required = true)
    private Integer status;

    @NonNull
    @Schema(name = "Error code", example = "400", required = true)
    private String code;

    @NonNull
    @Schema(name = "Error message", example = "Error message", required = true)
    private String message;

    @Schema(name = "Error details", example = "Error details")
    private String details;

}
