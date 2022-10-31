package api.controller;

import api.entity.CardEntity;
import api.enums.CardStatus;
import api.exception.CardModelException;
import api.model.CardModel;
import api.model.CardCreateModel;
import api.service.CardService;
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
import java.math.BigDecimal;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/cartoes")
@Tag(name = "Cartões", description = "Cadastro de Cartões")
@Validated
@SwaggerDefinition(basePath = "/cartoes", info = @Info(title = "Api BenefitsCard", version = "v1"))
public class CardController {

    @Autowired
    private CardService cardService;

    private static final Logger LOGGER = LoggerFactory.getLogger(CardController.class);

    @ApiOperation(value = "Consult the data of a benefit card through cardId.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cardNumber", required = true, dataType = "String", paramType = "path", value = "Benefits card number."),
            @ApiImplicitParam(name = "Authorization", required = true, dataType = "String", paramType = "header", value = "Authentication Token")})
    @ApiResponses({
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 422, message = "Invalid data"),
            @ApiResponse(code = 500, message = "Server error")
    })
    @GetMapping(value = "/{numeroCartao}")
    public ResponseEntity<BigDecimal> getCardByNumber(@PathVariable String numeroCartao) throws CardModelException {

        try {

            return ok(cardService.findCartaoByNumeroCartao(numeroCartao));

        } catch (NullPointerException e) {
            LOGGER.info(e.getMessage(), "ERR_NULL_DATA", "The invoked property is null.");
            throw new CardModelException();

        } catch (IllegalStateException e) {
            LOGGER.info(e.getMessage(), "ERR_SOME_PROBLEM_OCCURRED", "There was a problem while running.");
            throw new CardModelException();
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
    @GetMapping(value = "/id/{id}")
    public ResponseEntity<CardModel> getCardByNumber(@PathVariable Long id) throws CardModelException {

        try {

            return ok(cardService.findCartaoById(id));

        } catch (NullPointerException e) {
            LOGGER.info(e.getMessage(), "ERR_NULL_DATA", "The invoked property is null.");
            throw new CardModelException();

        } catch (IllegalStateException e) {
            LOGGER.info(e.getMessage(), "ERR_SOME_PROBLEM_OCCURRED", "There was a problem while running.");
            throw new CardModelException();
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
    public ResponseEntity<List<CardModel>> listCards() throws CardModelException {

        try {

            return ok(cardService.findAllByOrderByNumeroCartaoAsc());

        } catch (NullPointerException e) {
            LOGGER.info(e.getMessage(), "ERR_NULL_DATA", "The invoked property is null.");
            throw new CardModelException();

        } catch (IllegalStateException e) {
            LOGGER.info(e.getMessage(), "ERR_SOME_PROBLEM_OCCURRED", "There was a problem while running.");
            throw new CardModelException();
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
    @GetMapping(value = "/status/{status}")
    public ResponseEntity<List<CardModel>> listCardsByStatus(@PathVariable("status") String status) throws CardModelException {

        try {

            return ok(cardService.findAllByStatusOrderByNumeroCartaoAsc(CardStatus.fromValue(status)));

        } catch (NullPointerException e) {
            LOGGER.info(e.getMessage(), "ERR_NULL_DATA", "The invoked property is null.");
            throw new CardModelException();

        } catch (IllegalStateException e) {
            LOGGER.info(e.getMessage(), "ERR_SOME_PROBLEM_OCCURRED", "There was a problem while running.");
            throw new CardModelException();
        }
    }

    @ApiOperation(value = "Create a benefit card in the database.", response = Long.class)
    @ApiResponses({
            @ApiResponse(code = 201, message = "Created card"),
            @ApiResponse(code = 422, message = "Invalid data"),
            @ApiResponse(code = 500, message = "Server error")
    })

    @PostMapping
    public ResponseEntity<CardModel> createCard(@Valid @RequestBody CardCreateModel cardCreateModel) throws CardModelException {

        try {

            return new ResponseEntity< >(cardService.save(cardCreateModel), HttpStatus.CREATED );

        } catch (NullPointerException e) {
            LOGGER.info(e.getMessage(), "ERR_NULL_DATA", "The invoked property is null.");
            throw new CardModelException();

        } catch (IllegalStateException e) {
            LOGGER.info(e.getMessage(), "ERR_SOME_PROBLEM_OCCURRED", "There was a problem while running.");
            throw new CardModelException();
        }
    }

    @ApiOperation("Updates benefit card data in the database.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 422, message = "Invalid data"),
            @ApiResponse(code = 500, message = "Server error")
    })
    @PutMapping(value = "/{id}")
    public ResponseEntity<CardModel> updateCard(@PathVariable("id") Long id, @Valid @RequestBody CardEntity cartaoEntity) throws CardModelException {

        try {

            cardService.update(id, cartaoEntity);

            return ResponseEntity.ok().build();

        } catch (NullPointerException e) {
            LOGGER.info(e.getMessage(), "ERR_NULL_DATA", "The invoked property is null.");
            throw new CardModelException();

        } catch (IllegalStateException e) {
            LOGGER.info(e.getMessage(), "ERR_SOME_PROBLEM_OCCURRED", "There was a problem while running.");
            throw new CardModelException();
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
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteCard(@PathVariable("id") Long id) throws CardModelException {

            return new ResponseEntity< >(cardService.deleteCartaoById(id), HttpStatus.OK );
    }
}