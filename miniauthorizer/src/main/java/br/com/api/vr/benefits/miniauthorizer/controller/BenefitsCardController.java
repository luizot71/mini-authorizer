package br.com.api.vr.benefits.miniauthorizer.controller;

import br.com.api.vr.benefits.miniauthorizer.entity.BenefitsCardEntity;
import br.com.api.vr.benefits.miniauthorizer.exception.handler.ApplicationException;
import br.com.api.vr.benefits.miniauthorizer.model.BenefitsCardCreateModel;
import br.com.api.vr.benefits.miniauthorizer.model.BenefitsCardModel;
import br.com.api.vr.benefits.miniauthorizer.service.impl.BenefitsCardService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/benefits/card/v1/meal-ticket")
@SwaggerDefinition(basePath = "/benefits/card/v1/meal-ticket", info = @Info(title = "Api BenefitsCard", version = "v1"))
@Validated
public class BenefitsCardController {

    private final BenefitsCardService service;
    private static final Logger LOGGER = LoggerFactory.getLogger(BenefitsCardController.class);

    @Autowired
    public BenefitsCardController(BenefitsCardService service) {
        this.service = service;
    }

    @ApiOperation(value = "Create a benefit card in the database.", response = Long.class)
    @ApiResponses({
            @ApiResponse(code = 201, message = "Created card"),
            @ApiResponse(code = 422, message = "Invalid data"),
            @ApiResponse(code = 500, message = "Server error")
    })
    @PostMapping
    public ResponseEntity<BenefitsCardModel> createBenefitsCard(@Valid @RequestBody final BenefitsCardCreateModel benefitsCardCreateModel,
                                                                @RequestHeader("Authorization") final String token) throws ApplicationException {
        try {

            return status(CREATED).body(service.save(benefitsCardCreateModel, token));

        } catch (NullPointerException e) {
            LOGGER.info(e.getMessage(), "ERR_NULL_DATA", "The invoked property is null.");
            throw new ApplicationException();

        } catch (IllegalStateException e) {
            LOGGER.info(e.getMessage(), "ERR_SOME_PROBLEM_OCCURRED", "There was a problem while running.");
            throw new ApplicationException();
        }
    }

    @ApiOperation("Updates benefit card data in the database.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 422, message = "Invalid data"),
            @ApiResponse(code = 500, message = "Server error")
    })
    @PutMapping("/{cardId}")
    public ResponseEntity<Void> updateBenefitsCard(@PathVariable( "cardId" ) Long cardId,	@Valid @RequestBody BenefitsCardEntity benefitsCardEntity,
                                                           @RequestHeader("Authorization") String token) throws ApplicationException {
        try {

                service.updateCard(cardId, benefitsCardEntity, token);

                return ResponseEntity.ok().build();

        } catch (NullPointerException e) {
            LOGGER.info(e.getMessage(), "ERR_NULL_DATA", "A propriedade invocada esta nula.");
            throw new ApplicationException();

        } catch (IllegalStateException e) {
            LOGGER.info(e.getMessage(), "ERR_SOME_PROBLEM_OCCURRED", "Ocorreu algum problema durante a execução.");
            throw new ApplicationException();
        }
    }
}
