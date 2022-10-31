package api.controller;

import api.model.CardCreateTransactionModel;
import api.model.CardTransactionModel;
import api.service.CardTransactionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping( "/transacoes" )
@Tag( name = "Transações", description = "Cadastro de Transações" )
@Validated
public class TransactionCardController {

    @Autowired
    private CardTransactionService transactionService;

    @GetMapping( value = "/{id}" )
    public ResponseEntity<CardTransactionModel> getTransactionById(@PathVariable Long id) {
        return new ResponseEntity< >(transactionService.findById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity< List<CardTransactionModel> > listTransactions() {
        return new ResponseEntity< >( transactionService.findAll(), HttpStatus.OK );
    }

    @PostMapping
    public ResponseEntity< String > createTransaction(@Valid @RequestBody CardCreateTransactionModel cardCreateTransactionModel) {
        return new ResponseEntity< >(transactionService.save(cardCreateTransactionModel), HttpStatus.CREATED );
    }

    @DeleteMapping( value = "/{id}" )
    public ResponseEntity< String > deleteTransaction( @PathVariable( "id" ) Long id ) {
        return new ResponseEntity< >( transactionService.deleteById( id ), HttpStatus.OK );
    }

}