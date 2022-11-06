package api.controller;

import api.MiniAutorizadorApplicationTests;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TransacaoControllerTest extends MiniAutorizadorApplicationTests {

    private static final String URL_API = "/transacoes";

    private MockMvc mockMvc;

    @Autowired
    private TransacaoController transacaoController;

    @Autowired
    private CartaoController cartaoController;

    @BeforeEach
    public void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders.standaloneSetup(transacaoController).build();
    }

    @AfterAll
    public void tearDown() throws Exception {
        this.testDELETETransaction();
    }

    @Test
    @DisplayName("Cria a Transação")
    public void WhenCreatedTransaction() throws Exception {
        String cartao = "{\"numeroCartao\": \"3000090004500110\", \"senha\": \"9020\", \"status\": \"ATIVO\"}";

        this.mockMvc = MockMvcBuilders.standaloneSetup(cartaoController).build();
        this.mockMvc.perform(MockMvcRequestBuilders.post("/cartoes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(cartao)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        String transacao = "{\"numeroCartao\": \"3000090004500110\", \"senha\": \"9020\", \"valor\": \"10.20\"}";

        this.mockMvc = MockMvcBuilders.standaloneSetup(transacaoController).build();
        this.mockMvc.perform(MockMvcRequestBuilders.post(URL_API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(transacao)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    @DisplayName("Pega todas as Transações")
    public void WhenGetAllTransactions() throws Exception {
        String cartao = "{\"numeroCartao\": \"2200090004500110\", \"senha\": \"8990\", \"status\": \"ATIVO\"}";

        this.mockMvc = MockMvcBuilders.standaloneSetup(cartaoController).build();
        this.mockMvc.perform(MockMvcRequestBuilders.post("/cartoes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(cartao)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        String transacao = "{\"numeroCartao\": \"2200090004500110\", \"senha\": \"8990\", \"valor\": \"10.20\"}";

        this.mockMvc = MockMvcBuilders.standaloneSetup(transacaoController).build();
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
        String cartao = "{\"numeroCartao\": \"3300090004500110\", \"senha\": \"9999\", \"status\": \"ATIVO\"}";

        this.mockMvc = MockMvcBuilders.standaloneSetup(cartaoController).build();
        this.mockMvc.perform(MockMvcRequestBuilders.post("/cartoes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(cartao)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        String transacao = "{\"numeroCartao\": \"3300090004500110\", \"senha\": \"9999\", \"valor\": \"10.20\"}";

        this.mockMvc = MockMvcBuilders.standaloneSetup(transacaoController).build();
        this.mockMvc.perform(MockMvcRequestBuilders.post(URL_API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(transacao)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        this.mockMvc.perform(MockMvcRequestBuilders.get(URL_API+"/{id}", "1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @DisplayName("Exclui o Transação por ID")
    public void testDELETETransaction() throws Exception {
        String cartao = "{\"numeroCartao\": \"5500090004500550\", \"senha\": \"5678\", \"status\": \"ATIVO\"}";

        this.mockMvc = MockMvcBuilders.standaloneSetup(cartaoController).build();
        this.mockMvc.perform(MockMvcRequestBuilders.post("/cartoes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(cartao)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        String transacao = "{\"numeroCartao\": \"5500090004500550\", \"senha\": \"5678\", \"valor\": \"10.20\"}";

        this.mockMvc = MockMvcBuilders.standaloneSetup(transacaoController).build();
        this.mockMvc.perform(MockMvcRequestBuilders.post(URL_API)
                .contentType(MediaType.APPLICATION_JSON)
                .content(transacao)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        this.mockMvc.perform(MockMvcRequestBuilders.delete(URL_API+"/{id}", "1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
