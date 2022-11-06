package api;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(classes = MiniAutorizadorApplication.class)
@TestPropertySource(locations = "classpath:application-test.properties")
public class MiniAutorizadorApplicationTests {

	@Test
	public void contextLoads() {
	}

}
