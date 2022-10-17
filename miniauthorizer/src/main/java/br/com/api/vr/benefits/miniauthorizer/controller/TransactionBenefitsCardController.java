package br.com.api.vr.benefits.miniauthorizer.controller;

import br.com.api.vr.benefits.miniauthorizer.exception.handler.ApplicationException;
import br.com.api.vr.benefits.miniauthorizer.model.BenefitsCardCreateTransactionModel;
import br.com.api.vr.benefits.miniauthorizer.service.impl.BenefitsCardTransactionService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/benefits/card/v1/meal-ticket/transaction")
@SwaggerDefinition(basePath = "/benefits/card/v1/meal-ticket", info = @Info(title = "Api BenefitsCard", version = "v1"))
@Validated
public class TransactionBenefitsCardController {

    @Autowired
    private final BenefitsCardTransactionService benefitsCardTransactionService;

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionBenefitsCardController.class);

    public TransactionBenefitsCardController(BenefitsCardTransactionService benefitsCardTransactionService) {
        this.benefitsCardTransactionService = benefitsCardTransactionService;
    }

    @ApiOperation(value = "Create transaction in the database.", response = Long.class)
    @ApiResponses({
            @ApiResponse(code = 201, message = "Created transaction card"),
            @ApiResponse(code = 422, message = "Invalid data"),
            @ApiResponse(code = 500, message = "Server error")
    })
    @PostMapping
    public ResponseEntity<String> createBenefitsCardTransaction(@Valid @RequestBody final BenefitsCardCreateTransactionModel benefitsCardCreateTransactionModel,
                                                                @RequestHeader("Authorization") final String token) throws ApplicationException {
        try {

            return status(CREATED).body(benefitsCardTransactionService.save(benefitsCardCreateTransactionModel, token));

        } catch (NullPointerException e) {
            LOGGER.info(e.getMessage(), "ERR_NULL_DATA", "The invoked property is null.");
            throw new ApplicationException();

        } catch (IllegalStateException e) {
            LOGGER.info(e.getMessage(), "ERR_SOME_PROBLEM_OCCURRED", "There was a problem while running.");
            throw new ApplicationException();
        }
    }

    @ApiOperation(value = "Deletes transaction benefit card from the database", response = String.class)
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
    public ResponseEntity<String> deleteTransactionBenefitsCardByCardId(@PathVariable("cardId") Long cardId,
                                                                        @RequestHeader("Authorization") final String token) throws ApplicationException {
        try {

            benefitsCardTransactionService.deleteBenefitsCardById(cardId, token);

            return ResponseEntity.noContent().build();

        } catch (NullPointerException e) {
            LOGGER.info(e.getMessage(), "ERR_NULL_DATA", "The invoked property is null.");
            throw new ApplicationException();

        } catch (IllegalStateException e) {
            LOGGER.info(e.getMessage(), "ERR_SOME_PROBLEM_OCCURRED", "There was a problem while running.");
            throw new ApplicationException();
        }
    }

    @ApiOperation(value = "Get transaction benefit card through cardId.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "idCard", required = true, dataType = "Long", paramType = "path", value = "Benefits card number."),
            @ApiImplicitParam(name = "Authorization", required = true, dataType = "String", paramType = "header", value = "Authentication Token")})
    @ApiResponses({
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 422, message = "Invalid data"),
            @ApiResponse(code = 500, message = "Server error")
    })
    @GetMapping("/{cardId}")
    public ResponseEntity<BenefitsCardCreateTransactionModel> getTransactionBenefitsCardById(@PathVariable("cardId") final Long cardId,
                                                                  @RequestHeader("Authorization") final String token) throws ApplicationException {
        try {

            return ok(benefitsCardTransactionService.findById(cardId, token));

        } catch (NullPointerException e) {
            LOGGER.info(e.getMessage(), "ERR_NULL_DATA", "The invoked property is null.");
            throw new ApplicationException();

        } catch (IllegalStateException e) {
            LOGGER.info(e.getMessage(), "ERR_SOME_PROBLEM_OCCURRED", "There was a problem while running.");
            throw new ApplicationException();
        }
    }

    @ApiOperation(value = "Get list transaction benefit card.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 500, message = "Server error")
    })
    @GetMapping("/{cardId}")
    public ResponseEntity<List<BenefitsCardCreateTransactionModel>> getListTransactions() throws ApplicationException {
        try {

            return ok(benefitsCardTransactionService.findAll());

        } catch (NullPointerException e) {
            LOGGER.info(e.getMessage(), "ERR_NULL_DATA", "The invoked property is null.");
            throw new ApplicationException();

        } catch (IllegalStateException e) {
            LOGGER.info(e.getMessage(), "ERR_SOME_PROBLEM_OCCURRED", "There was a problem while running.");
            throw new ApplicationException();
        }
    }
}
