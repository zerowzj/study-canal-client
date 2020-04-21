package study.canal.client.support.canal.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

@Slf4j
public class SpringCanalClient extends CanalClient implements InitializingBean, DisposableBean {

    @Override
    public void afterPropertiesSet() throws Exception {
        /*
         * 使用线程异步启动，否则会阻塞于此
         */
        Thread t = new Thread(() -> {
            super.connect();
        }, "START-THREAD");
        t.start();
    }

    @Override
    public void destroy() throws Exception {
        super.disconnect();
    }
}
