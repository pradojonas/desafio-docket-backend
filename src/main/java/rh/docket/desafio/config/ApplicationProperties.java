package rh.docket.desafio.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@PropertySources({ @PropertySource(value = "classpath:/app-${spring.profiles.active}.properties",
                                   ignoreResourceNotFound = false) })
public class ApplicationProperties {

    @Autowired
    Environment env;

    @Value("${api.certidoes}")
    private String certidoesApiUrl;

    public List<String> getEnv() {
        return Arrays.asList(env.getActiveProfiles());
    }

    public String getCertidoesApiUrl() {
        return certidoesApiUrl;
    }

    public void setCertidoesApiUrl(String certidoesApiUrl) {
        this.certidoesApiUrl = certidoesApiUrl;
    }
}