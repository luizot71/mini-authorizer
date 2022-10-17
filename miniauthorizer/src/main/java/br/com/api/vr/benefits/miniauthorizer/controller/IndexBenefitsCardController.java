package br.com.api.vr.benefits.miniauthorizer.controller;

import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import static org.springframework.http.ResponseEntity.ok;

@Controller
@SwaggerDefinition(info = @Info(title = "Api BenefitsCard", version = "v1", description = "Api Rest VR Benefits"))
public class IndexBenefitsCardController {

    private static final Logger LOGGER = LoggerFactory.getLogger(IndexBenefitsCardController.class);

    @ApiOperation(value = "Index Response.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", required = true, dataType = "String", paramType = "header", value = "Authentication Token")})
    @ApiResponses({
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 422, message = "Invalid data"),
            @ApiResponse(code = 500, message = "Server error")
    })
    @GetMapping("/")
    public ResponseEntity<?> indexBenefitsCard() {

            return ok("Api MiniAuthorizer - OK!");
    }
}
