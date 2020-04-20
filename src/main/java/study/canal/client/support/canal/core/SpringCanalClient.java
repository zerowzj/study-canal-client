package study.canal.client.support.canal.core;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class SpringCanalClient extends CanalClient implements InitializingBean, DisposableBean {

    @Override
    public void afterPropertiesSet() throws Exception {
        super.connect();
    }

    @Override
    public void destroy() throws Exception {
        super.disconnect();
    }
}
