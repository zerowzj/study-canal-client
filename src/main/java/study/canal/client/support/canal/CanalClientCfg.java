package study.canal.client.support.canal;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import study.canal.client.support.canal.client.SpringCanalClient;

@Slf4j
@Configuration
public class CanalClientCfg {


    @Bean
    public SpringCanalClient canalClient() {
        SpringCanalClient client = new SpringCanalClient();
        return client;
    }
}
