package br.com.api.vr.benefits.miniauthorizer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("br.com.api.vr.benefits.miniauthorizer")
@EntityScan("br.com.api.vr.benefits.miniauthorizer")
@ComponentScan("br.com.api.vr.benefits.miniauthorizer")
@EnableCaching
public class MiniauthorizerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MiniauthorizerApplication.class, args);
	}
}
