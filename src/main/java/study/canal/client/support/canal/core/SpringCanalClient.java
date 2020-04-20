package study.canal.client.support.canal.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import java.util.concurrent.Executors;

@Slf4j
public class SpringCanalClient extends CanalClient implements InitializingBean, DisposableBean {

    @Override
    public void afterPropertiesSet() throws Exception {
        Executors.newSingleThreadExecutor().execute(() -> {
            super.connect();
        });
        log.info("start");
    }

    @Override
    public void destroy() throws Exception {
        super.disconnect();
    }
}
