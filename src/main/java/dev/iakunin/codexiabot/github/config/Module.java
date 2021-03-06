package dev.iakunin.codexiabot.github.config;

import java.io.IOException;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @checkstyle DesignForExtension (500 lines)
 */
@Configuration
public class Module {

    @Bean
    @Autowired
    public GitHub gitHub(
        @Value("${app.github-token}") final String token
    ) throws IOException {
        return new GitHubBuilder().withOAuthToken(token).build();
    }
}
