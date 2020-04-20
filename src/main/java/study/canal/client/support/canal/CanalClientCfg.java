package study.canal.client.support.canal;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import study.canal.client.support.canal.client.SpringCanalClient;

@Slf4j
@Configuration
public class CanalClientCfg {

    @Bean
    @ConfigurationProperties(prefix = "cancal")
    public CanalClientProperties properties() {
        return new CanalClientProperties();
    }

    @Bean
    public SpringCanalClient canalClient(CanalClientProperties props) {
        log.info("{}", props);
        SpringCanalClient client = new SpringCanalClient();
        client.setPort(props.getServer().getPort());
        return client;
    }
}
