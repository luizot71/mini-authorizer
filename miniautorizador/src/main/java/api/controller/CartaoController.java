package api.controller;

import api.entity.CartaoEntity;
import api.enums.CartaoStatus;
import api.exception.ModelException;
import api.model.CriaCartaoModel;
import api.model.CartaoModel;
import api.service.CartaoService;
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

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/cartoes")
@Tag(name = "Cartões", description = "Cadastro de Cartões")
@Validated
public class CartaoController {

    @Autowired
    private CartaoService cartaoService;

    private static final Logger LOGGER = LoggerFactory.getLogger(CartaoController.class);

    @GetMapping(value = "/{numeroCartao}")
    public ResponseEntity<BigDecimal> getCardByNumber(@PathVariable String numeroCartao) throws ModelException {

            return ok(cartaoService.findCartaoByNumeroCartao(numeroCartao));
    }

    @GetMapping(value = "/id/{id}")
    public ResponseEntity<CartaoModel> getCardByNumber(@PathVariable Long id) throws ModelException {

            return ok(cartaoService.findCartaoById(id));
    }

    @GetMapping
    public ResponseEntity<List<CartaoModel>> listCards() throws ModelException {

            return ok(cartaoService.findAllByOrderByNumeroCartaoAsc());

    }

    @GetMapping(value = "/status/{status}")
    public ResponseEntity<List<CartaoModel>> listCardsByStatus(@PathVariable("status") String status) throws ModelException {

            return ok(cartaoService.findAllByStatusOrderByNumeroCartaoAsc(CartaoStatus.fromValue(status)));

    }

    @PostMapping
    public ResponseEntity<CartaoModel> createCard(@Valid @RequestBody CriaCartaoModel criaCartaoModel) {
        return new ResponseEntity< >(cartaoService.save(criaCartaoModel), HttpStatus.CREATED );
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<CartaoModel> updateCard(@PathVariable("id") Long id, @Valid @RequestBody CartaoEntity cartaoEntity) throws ModelException {

            cartaoService.update(id, cartaoEntity);

            return ResponseEntity.ok().build();

    }

    @CrossOrigin(allowedHeaders = "*")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteCard(@PathVariable("id") Long id) throws ModelException {

            return new ResponseEntity< >(cartaoService.deleteCartaoById(id), HttpStatus.OK );
    }
}