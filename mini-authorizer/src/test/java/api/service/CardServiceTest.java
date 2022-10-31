package api.service;

import api.MiniAuthorizerApplicationTests;
import api.entity.CardEntity;
import api.entity.CardBalanceEntity;
import api.enums.CardStatus;
import api.model.CardModel;
import api.model.CardCreateModel;
import api.repository.CardRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class CardServiceTest extends MiniAuthorizerApplicationTests {

    @Autowired
    private CardService cardService;

    @MockBean
    private CardRepository cardRepository;

    private CardBalanceEntity saldo = new CardBalanceEntity();

    private ModelMapper mapper = new ModelMapper();

    @Test
    @DisplayName("Cria o Cartão com sucesso")
    void testSaveCartao() {
        CardCreateModel mockCartaoModel = CardCreateModel.builder()
                .numeroCartao("1111111111")
                .senha(null)
                .build();

        CardEntity cardEntity = mapper.map(mockCartaoModel, CardEntity.class);

        when(cardRepository.save(any(CardEntity.class))).thenReturn(cardEntity);

        CardModel saveCardModel = cardService.save(mockCartaoModel);
        CardCreateModel cardCreateModel = mapper.map(saveCardModel, CardCreateModel.class);

        Assertions.assertNotNull(cardEntity);
        assertEquals(mockCartaoModel, cardCreateModel);
    }

    @Test
    @DisplayName("Localiza todos os Cartões por Status com sucesso")
    void testFindAllByStatus() {
        List<CardEntity> mockListCartoesEntities = Stream.of(
                        CardEntity.builder()
                                .id(1L)
                                .numeroCartao("1111111111")
                                .saldo(saldo)
                                .status(CardStatus.ATIVO)
                                .build(),
                        CardEntity.builder()
                                .id(2L)
                                .numeroCartao("2222222222")
                                .saldo(saldo)
                                .status(CardStatus.ATIVO)
                                .build())
                .collect(Collectors.toList());

        when(cardRepository.findAllByStatusOrderByNumeroCartaoAsc(CardStatus.ATIVO))
                .thenReturn(mockListCartoesEntities);

        List<CardModel> cartoes = cardService.findAllByStatusOrderByNumeroCartaoAsc(CardStatus.ATIVO);
        List<CardEntity> listaCartoes = cartoes.stream().map(entity -> mapper.map(entity, CardEntity.class)).collect(Collectors.toList());

        Assertions.assertNotNull(listaCartoes);
        assertEquals(mockListCartoesEntities, listaCartoes);
    }

    @Test
    @DisplayName("Localiza todos os Cartões por Status inválido")
    void testFindAllByStatusInvalid() {
        List<CardEntity> mockListCartoesEntities = Stream.of(
                        CardEntity.builder()
                                .id(1L)
                                .numeroCartao("1111111111")
                                .saldo(saldo)
                                .status(CardStatus.ATIVO)
                                .build(),
                        CardEntity.builder()
                                .id(2L)
                                .numeroCartao("2222222222")
                                .saldo(saldo)
                                .status(CardStatus.ATIVO)
                                .build())
                .collect(Collectors.toList());

        when(cardRepository.findAllByStatusOrderByNumeroCartaoAsc(CardStatus.ATIVO))
                .thenReturn(mockListCartoesEntities);

        try {
            List<CardModel> cartoes = cardService.findAllByStatusOrderByNumeroCartaoAsc(CardStatus.INATIVO);
            List<CardEntity> listaCartoes = cartoes.stream().map(entity -> mapper.map(entity, CardEntity.class)).collect(Collectors.toList());
            assertNotEquals(mockListCartoesEntities, listaCartoes);
        } catch (Exception e) {
            assertEquals("Nenhum cartão encontrado.", e.getMessage());
        }
    }

    @Test
    @DisplayName("Localiza o Cartão por ID com sucesso")
    void testFindCartaoById() {
        CardEntity mockCartaoEntity = CardEntity.builder()
                .id(1L)
                .numeroCartao("1111111111")
                .saldo(saldo)
                .status(CardStatus.ATIVO)
                .build();

        when(cardRepository.findById(1L)).thenReturn(Optional.of(mockCartaoEntity));

        CardModel findCartao = cardService.findCartaoById(1L);
        CardEntity cardEntity = mapper.map(findCartao, CardEntity.class);

        Assertions.assertNotNull(cardEntity);
        assertEquals(mockCartaoEntity, cardEntity);
    }

    @Test
    @DisplayName("Localiza o Cartão por ID inválido")
    void testFindCartaoByIdInvalid() {
        CardEntity mockCartaoEntity = CardEntity.builder()
                .id(1L)
                .numeroCartao("1111111111")
                .saldo(saldo)
                .status(CardStatus.ATIVO)
                .build();

        when(cardRepository.findById(1L)).thenReturn(Optional.of(mockCartaoEntity));

        try {
            cardService.findCartaoById(2L);
        } catch (Exception e) {
            assertEquals("Nenhum cartão encontrado.", e.getMessage());
        }
    }

    @Test
    @DisplayName("Localiza o Cartão por Número do Cartão com sucesso")
    void testFindCartaoByNumber() {
        CardEntity mockCartaoEntity = CardEntity.builder()
                .numeroCartao("1111111111")
                .saldo(saldo)
                .status(CardStatus.ATIVO)
                .build();

        when(cardRepository.findByNumeroCartao("1111111111")).thenReturn(Optional.of(mockCartaoEntity));

        BigDecimal findCartao = cardService.findCartaoByNumeroCartao("1111111111");

        Assertions.assertNotNull(findCartao);
        assertEquals(mockCartaoEntity.getSaldo().getValor(), findCartao);
    }

    @Test
    @DisplayName("Localiza o Cartão por Número do Cartão inválido")
    void testFindCartaoByNumberInvalid() {
        CardEntity mockCartaoEntity = CardEntity.builder()
                .numeroCartao("1111111111")
                .saldo(saldo)
                .status(CardStatus.ATIVO)
                .build();

        when(cardRepository.findByNumeroCartao("1111111111")).thenReturn(Optional.of(mockCartaoEntity));

        try {
            cardService.findCartaoByNumeroCartao("555555555");
        } catch (Exception e) {
            assertEquals("", e.getMessage());
        }
    }

    @Test
    @DisplayName("Altera o Cartão por ID com sucesso")
    void testUpdateCartao() {
        CardEntity mockCartaoEntity = CardEntity.builder()
                .numeroCartao("1111111111")
                .senha("xxxxxxxx")
                .saldo(saldo)
                .status(CardStatus.ATIVO)
                .build();

        when(cardRepository.existsById(1L)).thenReturn(true);

        mockCartaoEntity.setNumeroCartao("3333333333");
        when(cardRepository.save(any(CardEntity.class))).thenReturn(mockCartaoEntity);

        CardModel updateCardModel = cardService.update(1L, mockCartaoEntity);
        CardEntity cardEntity = mapper.map(updateCardModel, CardEntity.class);
        cardEntity.setSaldo(mockCartaoEntity.getSaldo());
        cardEntity.setSenha(mockCartaoEntity.getSenha());

        Assertions.assertNotNull(cardEntity);
        assertEquals(mockCartaoEntity, cardEntity);
    }

    @Test
    @DisplayName("Altera o Cartão por ID inválido")
    void testUpdateCartaoIdInvalid() {
        CardEntity mockCartaoEntity = CardEntity.builder()
                .numeroCartao("1111111111")
                .senha("2222222222")
                .saldo(saldo)
                .status(CardStatus.ATIVO)
                .build();

        when(cardRepository.existsById(1L)).thenReturn(true);

        mockCartaoEntity.setNumeroCartao("44444444444");
        when(cardRepository.save(any(CardEntity.class))).thenReturn(mockCartaoEntity);

        try {
            cardService.update(2L, mockCartaoEntity);
        } catch (Exception e) {
            assertEquals("Nenhum cartão encontrado.", e.getMessage());
        }
    }

    @Test
    @DisplayName("Exclui o Cartão por ID com sucesso")
    void testDeleteCartaoById() {
        CardEntity mockCartaoEntity = CardEntity.builder()
                .numeroCartao("1111111111")
                .senha("2222222222")
                .saldo(saldo)
                .status(CardStatus.ATIVO)
                .build();

        when(cardRepository.existsById(1L)).thenReturn(true);

        String deleteCartao = cardService.deleteCartaoById(1L);

        Assertions.assertNotNull(deleteCartao);
        assertEquals("Cartão excluído com sucesso.", deleteCartao);
    }

    @Test
    @DisplayName("Exclui o Cartão por ID inválido")
    void testDeleteCartaoByIdInvalid() {
        CardEntity mockCartaoEntity = CardEntity.builder()
                .numeroCartao("1111111111")
                .senha("2222222222")
                .saldo(saldo)
                .status(CardStatus.ATIVO)
                .build();

        when(cardRepository.existsById(1L)).thenReturn(true);

        try {
            cardService.deleteCartaoById(2L);
        } catch (Exception e) {
            assertEquals("Nenhum cartão encontrado.", e.getMessage());
        }
    }

}
