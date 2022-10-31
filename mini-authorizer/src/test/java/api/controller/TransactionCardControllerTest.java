package api.controller;

import api.MiniAuthorizerApplicationTests;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TransactionCardControllerTest extends MiniAuthorizerApplicationTests {

    private static final String URL_API = "/transacoes";

    private MockMvc mockMvc;

    @Autowired
    private TransactionCardController transactionCardController;

    @Autowired
    private CardController cardController;

    @BeforeEach
    public void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders.standaloneSetup(transactionCardController).build();
    }

   /* @AfterAll
    public void tearDown() throws Exception {
        this.WhenDeleteTransaction();
    }
*/
    @Test
    @DisplayName("Cria a Transação")
    public void WhenCreatedTransaction() throws Exception {
        String cartao = "{\"numeroCartao\": \"11111111111\", \"senha\": \"9020\", \"status\": \"ATIVO\"}";

        this.mockMvc = MockMvcBuilders.standaloneSetup(cardController).build();
        this.mockMvc.perform(MockMvcRequestBuilders.post("/cartoes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(cartao)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        String transacao = "{\"numeroCartao\": \"11111111111\", \"senha\": \"9020\", \"valor\": \"10.20\"}";

        this.mockMvc = MockMvcBuilders.standaloneSetup(transactionCardController).build();
        this.mockMvc.perform(MockMvcRequestBuilders.post(URL_API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(transacao)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    @DisplayName("Pega todas as Transações")
    public void WhenGetAllTransactions() throws Exception {
        String cartao = "{\"numeroCartao\": \"22222222222\", \"senha\": \"8990\", \"status\": \"ATIVO\"}";

        this.mockMvc = MockMvcBuilders.standaloneSetup(cardController).build();
        this.mockMvc.perform(MockMvcRequestBuilders.post("/cartoes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(cartao)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        String transacao = "{\"numeroCartao\": \"22222222222\", \"senha\": \"8990\", \"valor\": \"10.20\"}";

        this.mockMvc = MockMvcBuilders.standaloneSetup(transactionCardController).build();
        this.mockMvc.perform(MockMvcRequestBuilders.post(URL_API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(transacao)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        this.mockMvc.perform(MockMvcRequestBuilders.get(URL_API))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Pega o Transação por ID do Transação")
    public void WhenGetTransactionById() throws Exception {
        String cartao = "{\"numeroCartao\": \"333333333333\", \"senha\": \"9999\", \"status\": \"ATIVO\"}";

        this.mockMvc = MockMvcBuilders.standaloneSetup(cardController).build();
        this.mockMvc.perform(MockMvcRequestBuilders.post("/cartoes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(cartao)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        String transacao = "{\"numeroCartao\": \"333333333333\", \"senha\": \"9999\", \"valor\": \"10.20\"}";

        this.mockMvc = MockMvcBuilders.standaloneSetup(transactionCardController).build();
        this.mockMvc.perform(MockMvcRequestBuilders.post(URL_API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(transacao)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        this.mockMvc.perform(MockMvcRequestBuilders.get(URL_API+"/{id}", "1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    //@Test
    @DisplayName("Exclui o Transação por ID")
    public void WhenDeleteTransaction() throws Exception {
        String cartao = "{\"numeroCartao\": \"9800700044409004\", \"senha\": \"9909\", \"status\": \"ATIVO\"}";

        this.mockMvc = MockMvcBuilders.standaloneSetup(cardController).build();
        this.mockMvc.perform(MockMvcRequestBuilders.post("/cartoes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(cartao)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        String transacao = "{\"numeroCartao\": \"9800700044409004\", \"senha\": \"9909\", \"valor\": \"10.20\"}";

        this.mockMvc = MockMvcBuilders.standaloneSetup(transactionCardController).build();
        this.mockMvc.perform(MockMvcRequestBuilders.post(URL_API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(transacao)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        this.mockMvc.perform(MockMvcRequestBuilders.delete(URL_API+"/{id}", "1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
