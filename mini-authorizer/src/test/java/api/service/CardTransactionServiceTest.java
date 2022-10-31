package api.service;

import api.MiniAuthorizerApplicationTests;
import api.entity.CardEntity;
import api.entity.CardBalanceEntity;
import api.entity.CardTransactionEntity;
import api.enums.CardStatus;
import api.model.CardCreateTransactionModel;
import api.model.CardTransactionModel;
import api.repository.CardRepository;
import api.repository.CardBalanceRepository;
import api.repository.CardTransactionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class CardTransactionServiceTest extends MiniAuthorizerApplicationTests {

    @Autowired
    private CardTransactionService cardTransactionService;

    @Autowired
    private CardService cardService;

    @MockBean
    private CardTransactionRepository cardTransactionRepository;

    @MockBean
    private CardRepository cardRepository;

    @MockBean
    private CardBalanceRepository cardBalanceRepository;

    private CardBalanceEntity mockCardBalanceEntity = CardBalanceEntity.builder()
            .id(1L)
            .valor(BigDecimal.valueOf(500))
            .build();

    @BeforeEach
    public void setUp() {
        when(cardBalanceRepository.findById(1L)).thenReturn(Optional.of(mockCardBalanceEntity));
    }

    private ModelMapper mapper = new ModelMapper();

    @Test
    @DisplayName("Cria a Transação com sucesso")
    void testSaveTransacao() {
        CardEntity mockCartaoEntity = CardEntity.builder()
                .id(1L)
                .numeroCartao("110110110110110")
                .senha("aaaaaaaaaaa")
                .saldo(mockCardBalanceEntity)
                .status(CardStatus.ATIVO)
                .build();

        when(cardRepository.findByNumeroCartao("110110110110110")).thenReturn(Optional.of(mockCartaoEntity));

        CardCreateTransactionModel mockTransacaoModel = CardCreateTransactionModel.builder()
                .numeroCartao(mockCartaoEntity.getNumeroCartao())
                .senha(mockCartaoEntity.getSenha())
                .valor(BigDecimal.valueOf(10.50))
                .build();

        when(cardTransactionRepository.save(any(CardTransactionEntity.class))).thenReturn(mapper.map(mockTransacaoModel, CardTransactionEntity.class));

        String salvaTransacao = cardTransactionService.save(mockTransacaoModel);

        Assertions.assertNotNull(salvaTransacao);
        assertEquals("OK", salvaTransacao);
    }

    @Test
    @DisplayName("Localiza todas as Transações com sucesso")
    void testFindAll() {
        CardBalanceEntity saldo = new CardBalanceEntity();
        CardEntity mockCartaoEntity = CardEntity.builder()
                .id(1L)
                .numeroCartao("220220220220220")
                .saldo(saldo)
                .status(CardStatus.ATIVO)
                .build();

        when(cardRepository.findById(1L)).thenReturn(Optional.of(mockCartaoEntity));

        List<CardTransactionEntity> mockTransacaoEntities = Stream.of(
                    CardTransactionEntity.builder()
                        .id(1L)
                        .cartao(mockCartaoEntity)
                        .valor(BigDecimal.valueOf(10.50))
                        .build(),
                    CardTransactionEntity.builder()
                        .id(2L)
                        .cartao(mockCartaoEntity)
                        .valor(BigDecimal.valueOf(11.55))
                        .build())
                .collect(Collectors.toList());

        when(cardTransactionRepository.findAll()).thenReturn(mockTransacaoEntities);

        List<CardTransactionModel> transacoes = cardTransactionService.findAll();
        List<CardTransactionEntity> listaTransacoes = transacoes.stream().map(entity -> mapper.map(entity, CardTransactionEntity.class)).collect(Collectors.toList());

        Assertions.assertNotNull(listaTransacoes);
        assertEquals(mockTransacaoEntities, listaTransacoes);
    }

    @Test
    @DisplayName("Localiza todas as Transações inválidas")
    void testFindAllInvalid() {
        CardBalanceEntity saldo = new CardBalanceEntity();
        CardEntity mockCartaoEntity = CardEntity.builder()
                .id(1L)
                .numeroCartao("220220220220220")
                .saldo(saldo)
                .status(CardStatus.ATIVO)
                .build();

        when(cardRepository.findById(1L)).thenReturn(Optional.of(mockCartaoEntity));

        List<CardTransactionEntity> mockTransacaoEntities = Stream.of(
                        CardTransactionEntity.builder()
                                .id(1L)
                                .cartao(mockCartaoEntity)
                                .valor(BigDecimal.valueOf(10.50))
                                .build(),
                        CardTransactionEntity.builder()
                                .id(2L)
                                .cartao(mockCartaoEntity)
                                .valor(BigDecimal.valueOf(11.55))
                                .build())
                .collect(Collectors.toList());

        when(cardTransactionRepository.findAll()).thenReturn(new ArrayList<>());

        try {
            List<CardTransactionModel> transacoes = cardTransactionService.findAll();
            List<CardTransactionEntity> listaTransacoes = transacoes.stream().map(entity -> mapper.map(entity, CardTransactionEntity.class)).collect(Collectors.toList());

            Assertions.assertNotNull(listaTransacoes);
            assertEquals(mockTransacaoEntities, listaTransacoes);
        } catch (Exception e) {
            assertEquals("Nenhuma transação encontrada.", e.getMessage());
        }
    }

    @Test
    @DisplayName("Localiza a Transação por ID com sucesso")
    void testFindTransacaoById() {
        CardEntity mockCartaoEntity = CardEntity.builder()
                .id(1L)
                .numeroCartao("220220220220220")
                .saldo(mockCardBalanceEntity)
                .status(CardStatus.ATIVO)
                .build();

        when(cardRepository.findById(1L)).thenReturn(Optional.of(mockCartaoEntity));

        CardTransactionEntity mockCardTransactionEntity = CardTransactionEntity.builder()
                .id(1L)
                .cartao(mockCartaoEntity)
                .valor(BigDecimal.valueOf(10.50))
                .build();

        when(cardTransactionRepository.findById(1L)).thenReturn(Optional.of(mockCardTransactionEntity));

        CardTransactionModel findTransacao = cardTransactionService.findById(1L);
        CardTransactionEntity cardTransactionEntity = mapper.map(findTransacao, CardTransactionEntity.class);
        cardTransactionEntity.setCartao(mockCartaoEntity);

        Assertions.assertNotNull(cardTransactionEntity);
        assertEquals(mockCardTransactionEntity, cardTransactionEntity);
    }

    @Test
    @DisplayName("Localiza o Transação por ID inválido")
    void testFindTransacaoByIdInvalid() {
        CardEntity mockCartaoEntity = CardEntity.builder()
                .id(1L)
                .numeroCartao("330330330330330")
                .senha("cccccccccc")
                .saldo(mockCardBalanceEntity)
                .status(CardStatus.ATIVO)
                .build();

        when(cardRepository.findById(1L)).thenReturn(Optional.of(mockCartaoEntity));

        CardTransactionEntity mockCardTransactionEntity = CardTransactionEntity.builder()
                .id(1L)
                .cartao(mockCartaoEntity)
                .valor(BigDecimal.valueOf(10.50))
                .build();

        when(cardTransactionRepository.findById(1L)).thenReturn(Optional.of(mockCardTransactionEntity));

        try {
            cardTransactionService.findById(2L);
        } catch (Exception e) {
            assertEquals("Nenhuma transação encontrada.", e.getMessage());
        }
    }

    @Test
    @DisplayName("Exclui o Transação por ID com sucesso")
    void testDeleteTransacaoById() {
        CardEntity mockCartaoEntity = CardEntity.builder()
                .id(1L)
                .numeroCartao("440440440440440")
                .senha("ddddddddddd")
                .saldo(mockCardBalanceEntity)
                .status(CardStatus.ATIVO)
                .build();

        when(cardRepository.findByNumeroCartao("440440440440440")).thenReturn(Optional.of(mockCartaoEntity));

        CardTransactionEntity mockCardTransactionEntity = CardTransactionEntity.builder()
                .id(1L)
                .cartao(mockCartaoEntity)
                .valor(BigDecimal.valueOf(10.50))
                .build();

        when(cardTransactionRepository.findById(1L)).thenReturn(Optional.of(mockCardTransactionEntity));

        String deleteTransacao = cardTransactionService.deleteById(1L);

        Assertions.assertNotNull(deleteTransacao);
        assertEquals("Transação excluída com sucesso.", deleteTransacao);
    }

    @Test
    @DisplayName("Exclui o Transação por ID inválido")
    void testDeleteTransacaoByIdInvalid() {
        CardEntity mockCartaoEntity = CardEntity.builder()
                .id(1L)
                .numeroCartao("550550550550550")
                .senha("eeeeeeeeeeee")
                .saldo(mockCardBalanceEntity)
                .status(CardStatus.ATIVO)
                .build();

        when(cardRepository.findById(1L)).thenReturn(Optional.of(mockCartaoEntity));

        when(cardTransactionRepository.existsById(1L)).thenReturn(true);

        try {
            cardTransactionService.deleteById(2L);
        } catch (Exception e) {
            assertEquals("Nenhuma transação encontrada.", e.getMessage());
        }
    }

}
