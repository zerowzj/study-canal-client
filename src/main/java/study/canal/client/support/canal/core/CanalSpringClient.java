package study.canal.client.support.canal.core;

import lombok.Getter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class CanalSpringClient extends CanalClient implements ApplicationContextAware, InitializingBean, DisposableBean {

    @Override
    public void afterPropertiesSet() throws Exception {
        super.connect();
    }

    @Override
    public void destroy() throws Exception {
        super.disconnect();
    }

    @Getter
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
