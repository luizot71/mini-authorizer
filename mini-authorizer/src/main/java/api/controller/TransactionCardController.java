package api.controller;

import api.exception.CardModelException;
import api.model.CardCreateTransactionModel;
import api.model.CardTransactionModel;
import api.service.CardTransactionService;
import io.swagger.annotations.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/transacoes")
@Tag(name = "Transações", description = "Cadastro de Transações")
@SwaggerDefinition(basePath = "/transacoes", info = @Info(title = "Api BenefitsCard", version = "v1"))
@Validated
public class TransactionCardController {

    @Autowired
    private CardTransactionService cardTransactionService;

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionCardController.class);

    @ApiOperation(value = "Get transaction benefit card through cardId.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "idCard", required = true, dataType = "Long", paramType = "path", value = "Benefits card number."),
            @ApiImplicitParam(name = "Authorization", required = true, dataType = "String", paramType = "header", value = "Authentication Token")})
    @ApiResponses({
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 422, message = "Invalid data"),
            @ApiResponse(code = 500, message = "Server error")
    })
    @GetMapping(value = "/{id}")
    public ResponseEntity<CardTransactionModel> getTransactionById(@PathVariable Long id) throws CardModelException {

        try {

            return new ResponseEntity<>(cardTransactionService.findById(id), HttpStatus.OK);

        } catch (NullPointerException e) {
            LOGGER.info(e.getMessage(), "ERR_NULL_DATA", "The invoked property is null.");
            throw new CardModelException();

        } catch (IllegalStateException e) {
            LOGGER.info(e.getMessage(), "ERR_SOME_PROBLEM_OCCURRED", "There was a problem while running.");
            throw new CardModelException();
        }
    }

    @ApiOperation(value = "Get list transaction benefit card.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 500, message = "Server error")
    })
    @GetMapping
    public ResponseEntity<List<CardTransactionModel>> listTransactions() throws CardModelException {

        try {

            return ok(cardTransactionService.findAll());

        } catch (NullPointerException e) {
            LOGGER.info(e.getMessage(), "ERR_NULL_DATA", "The invoked property is null.");
            throw new CardModelException();

        } catch (IllegalStateException e) {
            LOGGER.info(e.getMessage(), "ERR_SOME_PROBLEM_OCCURRED", "There was a problem while running.");
            throw new CardModelException();
        }
    }

    @ApiOperation(value = "Create transaction in the database.", response = Long.class)
    @ApiResponses({
            @ApiResponse(code = 201, message = "Created transaction card"),
            @ApiResponse(code = 422, message = "Invalid data"),
            @ApiResponse(code = 500, message = "Server error")
    })
    @PostMapping
    public ResponseEntity<String> createTransaction(@Valid @RequestBody CardCreateTransactionModel cardCreateTransactionModel) {

        try {

            return status(CREATED).body(cardTransactionService.save(cardCreateTransactionModel));

        } catch (NullPointerException e) {
            LOGGER.info(e.getMessage(), "ERR_NULL_DATA", "The invoked property is null.");
            throw new CardModelException();

        } catch (IllegalStateException e) {
            LOGGER.info(e.getMessage(), "ERR_SOME_PROBLEM_OCCURRED", "There was a problem while running.");
            throw new CardModelException();
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
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteTransaction(@PathVariable("id") Long id) throws CardModelException {

        try {

            cardTransactionService.deleteById(id);

            return ResponseEntity.noContent().build();

        } catch (NullPointerException e) {
            LOGGER.info(e.getMessage(), "ERR_NULL_DATA", "The invoked property is null.");
            throw new CardModelException();

        } catch (IllegalStateException e) {
            LOGGER.info(e.getMessage(), "ERR_SOME_PROBLEM_OCCURRED", "There was a problem while running.");
            throw new CardModelException();
        }
    }
}