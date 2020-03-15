package study.canal.client.support.canal;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import study.canal.client.support.canal.core.CanalClient;

@Slf4j
@Configuration
public class CanalClintCfg {

    @Bean
    public CanalClient canalClient2(){
        CanalClient client = new CanalClient();
        client.connect();
        return client;
    }

    @Bean
    public CanalClient canalClient1(){
        CanalClient client = new CanalClient();
        client.connect();
        return client;
    }
}
