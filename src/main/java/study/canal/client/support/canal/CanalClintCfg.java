package study.canal.client.support.canal;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import study.canal.client.support.canal.core.CanalClient;
import study.canal.client.support.canal.core.CanalSpringClient;

@Slf4j
@Configuration
public class CanalClintCfg {

    @Bean
    public CanalSpringClient canalClient(){
        CanalSpringClient client = new CanalSpringClient();
        return client;
    }
}
