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
public class CartaoControllerTest extends MiniAutorizadorApplicationTests {

    private static final String URL_API = "/cartoes";

    private MockMvc mockMvc;

    @Autowired
    private CartaoController cartaoController;

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(cartaoController).build();
    }

    @Test
    @DisplayName("Cria o Cartão")
    public void WhenCreatedCard() throws Exception {
        String data = "{\"numeroCartao\": \"9002099078009085\", \"senha\": \"9999\", \"status\": \"ATIVO\"}";

        this.mockMvc.perform(MockMvcRequestBuilders.post(URL_API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(data)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    @DisplayName("Pega todos os Cartões")
    public void WhenGetAllCards() throws Exception {
        String data = "{\"numeroCartao\": \"9802099078011080\", \"senha\": \"7890\", \"status\": \"ATIVO\"}";

        this.mockMvc.perform(MockMvcRequestBuilders.post(URL_API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(data)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        this.mockMvc.perform(MockMvcRequestBuilders.get(URL_API))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Pega o Cartão por Número do Cartão")
    public void WhenGetCardByCardNumber() throws Exception {
        String data = "{\"numeroCartao\": \"3000090004500980\", \"senha\": \"7090\", \"status\": \"ATIVO\"}";

        this.mockMvc.perform(MockMvcRequestBuilders.post(URL_API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(data)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        this.mockMvc.perform(MockMvcRequestBuilders.get(URL_API+"/{numeroCartao}", "3000090004500980"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Pega o Cartão por ID do Cartão")
    public void WhenGetCardById() throws Exception {
        String data = "{\"numeroCartao\": \"4400090004500980\", \"senha\": \"6790\", \"status\": \"ATIVO\"}";

        this.mockMvc.perform(MockMvcRequestBuilders.post(URL_API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(data)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        this.mockMvc.perform(MockMvcRequestBuilders.get(URL_API+"/id/{id}", "1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Atualiza o Cartão por ID")
    public void WhenUpdatedCard() throws Exception {
        String data = "{\"numeroCartao\": \"9122099078000070\", \"senha\": \"7790\", \"status\": \"ATIVO\"}";

        this.mockMvc.perform(MockMvcRequestBuilders.post(URL_API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(data)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        String updated = "{\"numeroCartao\": \"9002099078000070\", \"senha\": \"8090\", \"status\": \"ATIVO\"}";

        this.mockMvc.perform(MockMvcRequestBuilders.put(URL_API+"/{id}", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updated)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Exclui o Cartão por ID")
    public void WhenDeleteCardById() throws Exception {
        String data = "{\"numeroCartao\": \"9002099078009099\", \"senha\": \"9890\", \"status\": \"ATIVO\"}";

        this.mockMvc.perform(MockMvcRequestBuilders.post(URL_API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(data)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        this.mockMvc.perform(MockMvcRequestBuilders.delete(URL_API+"/{id}", "1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
