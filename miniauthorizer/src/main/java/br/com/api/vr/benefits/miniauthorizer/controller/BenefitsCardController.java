package br.com.api.vr.benefits.miniauthorizer.controller;

import br.com.api.vr.benefits.miniauthorizer.entity.BenefitsCardEntity;
import br.com.api.vr.benefits.miniauthorizer.enums.BenefitsCardStatus;
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
import java.math.BigDecimal;
import java.util.List;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.ResponseEntity.ok;
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

    @ApiOperation(value = "Consult the data of a benefit card through cardId.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cardNumber", required = true, dataType = "String", paramType = "path", value = "Benefits card number."),
            @ApiImplicitParam(name = "Authorization", required = true, dataType = "String", paramType = "header", value = "Authentication Token")})
    @ApiResponses({
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 422, message = "Invalid data"),
            @ApiResponse(code = 500, message = "Server error")
    })
    @GetMapping("/{cardNumber}")
    public ResponseEntity<BigDecimal> findBenefitsCardByCardNumber(@PathVariable("cardNumber") final String cardNumber,
                                                                   @RequestHeader("Authorization") final String token) throws ApplicationException {
        try {

            return ok(service.findBenefitsCardByCardNumber(cardNumber, token));

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
            LOGGER.info(e.getMessage(), "ERR_NULL_DATA", "The invoked property is null.");
            throw new ApplicationException();

        } catch (IllegalStateException e) {
            LOGGER.info(e.getMessage(), "ERR_SOME_PROBLEM_OCCURRED", "There was a problem while running.");
            throw new ApplicationException();
        }
    }

    @ApiOperation(value = "Consult the data of a benefit card through cardId.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "idCard", required = true, dataType = "Long", paramType = "path", value = "Benefits card number."),
            @ApiImplicitParam(name = "Authorization", required = true, dataType = "String", paramType = "header", value = "Authentication Token")})
    @ApiResponses({
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 422, message = "Invalid data"),
            @ApiResponse(code = 500, message = "Server error")
    })
    @GetMapping("/{cardId}")
    public ResponseEntity<BenefitsCardModel> findBenefitsCardById(@PathVariable("cardId") final Long cardId,
                                                                  @RequestHeader("Authorization") final String token) throws ApplicationException {
        try {

            return ok(service.findCardById(cardId, token));

        } catch (NullPointerException e) {
            LOGGER.info(e.getMessage(), "ERR_NULL_DATA", "The invoked property is null.");
            throw new ApplicationException();

        } catch (IllegalStateException e) {
            LOGGER.info(e.getMessage(), "ERR_SOME_PROBLEM_OCCURRED", "There was a problem while running.");
            throw new ApplicationException();
        }
    }

    @ApiOperation(value = "Consult the list of benefit cards by cardNumber.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", required = true, dataType = "String", paramType = "header", value = "Authentication Token")})
    @ApiResponses({
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 422, message = "Invalid data"),
            @ApiResponse(code = 500, message = "Server error")
    })
    @GetMapping
    public ResponseEntity<List<BenefitsCardModel>> listBenefitsCard(@RequestHeader("Authorization") final String token) throws ApplicationException {
        try {

            return ok(service.findAllBenefitsCardsAsc(token));

        } catch (NullPointerException e) {
            LOGGER.info(e.getMessage(), "ERR_NULL_DATA", "The invoked property is null.");
            throw new ApplicationException();

        } catch (IllegalStateException e) {
            LOGGER.info(e.getMessage(), "ERR_SOME_PROBLEM_OCCURRED", "There was a problem while running.");
            throw new ApplicationException();
        }
    }

    @ApiOperation(value = "Deletes benefit card from the database", response = String.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "idCard", required = true, dataType = "Long", paramType = "path", value = "Benefits card number."),
            @ApiImplicitParam(name = "Authorization", required = true, dataType = "String", paramType = "header", value = "Authentication Token")})
    @ApiResponses({
            @ApiResponse(code = 204, message = "No Content"),
            @ApiResponse(code = 401, message = "Not Authorized"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 422, message = "Unprocessable Entity"),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    @CrossOrigin(allowedHeaders = "*")
    @DeleteMapping("/{cardId}")
    public ResponseEntity<String> deleteBenefitsCardByCardId(@PathVariable("cardId") Long cardId,
                                                    @RequestHeader("Authorization") final String token) throws ApplicationException {
        try {

                service.deleteCardByCardId(cardId, token);

                return ResponseEntity.noContent().build();

        } catch (NullPointerException e) {
            LOGGER.info(e.getMessage(), "ERR_NULL_DATA", "The invoked property is null.");
            throw new ApplicationException();

        } catch (IllegalStateException e) {
            LOGGER.info(e.getMessage(), "ERR_SOME_PROBLEM_OCCURRED", "There was a problem while running.");
            throw new ApplicationException();
        }
    }

    @ApiOperation(value = "Consult the list of benefit cards by cardNumber.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "status", required = true, dataType = "String", paramType = "path", value = "Benefits Card Status."),
            @ApiImplicitParam(name = "Authorization", required = true, dataType = "String", paramType = "header", value = "Authentication Token")})
    @ApiResponses({
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 422, message = "Invalid data"),
            @ApiResponse(code = 500, message = "Server error")
    })
    @GetMapping("/status/{status}")
    public ResponseEntity<List<BenefitsCardModel>> listBenefitsCardByStatusCard(@PathVariable( "status" ) String statusCard, @RequestHeader("Authorization") final String token) throws ApplicationException {
        try {

            return ok(service.findBenefitsCardByStatusCardOrderByAsc(BenefitsCardStatus.getValues(statusCard), token));

        } catch (NullPointerException e) {
            LOGGER.info(e.getMessage(), "ERR_NULL_DATA", "The invoked property is null.");
            throw new ApplicationException();

        } catch (IllegalStateException e) {
            LOGGER.info(e.getMessage(), "ERR_SOME_PROBLEM_OCCURRED", "There was a problem while running.");
            throw new ApplicationException();
        }
    }
}
