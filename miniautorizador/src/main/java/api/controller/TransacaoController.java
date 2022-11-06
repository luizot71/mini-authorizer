package api.controller;

import api.exception.ModelException;
import api.model.CriaTransacaoModel;
import api.model.TransacaoModel;
import api.service.TransacaoService;
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

@Validated
public class TransacaoController {

    @Autowired
    private TransacaoService transacaoService;

    private static final Logger LOGGER = LoggerFactory.getLogger(TransacaoController.class);

    @GetMapping(value = "/{id}")
    public ResponseEntity<TransacaoModel> getTransactionById(@PathVariable Long id) throws ModelException {

            return new ResponseEntity<>(transacaoService.findById(id), HttpStatus.OK);

    }

    @GetMapping
    public ResponseEntity<List<TransacaoModel>> listTransactions() throws ModelException {


            return ok(transacaoService.findAll());

    }

    @PostMapping
    public ResponseEntity<String> createTransaction(@Valid @RequestBody CriaTransacaoModel criaTransacaoModel) {


            return status(CREATED).body(transacaoService.save(criaTransacaoModel));

    }

    @CrossOrigin(allowedHeaders = "*")
    @DeleteMapping( value = "/{id}" )
    public ResponseEntity< String > deleteTransaction( @PathVariable( "id" ) Long id ) {
        return new ResponseEntity< >( transacaoService.deleteById( id ), HttpStatus.OK );
    }
}