package api;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(classes = MiniAuthorizerApplication.class)
@TestPropertySource(locations = "classpath:application-test.properties")
public class MiniAuthorizerApplicationTests {

	@Test
	public void contextLoads() {
	}

}
