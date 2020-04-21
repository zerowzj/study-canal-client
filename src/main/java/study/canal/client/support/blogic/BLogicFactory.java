package study.canal.client.support.blogic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Slf4j
@Configuration
public class BLogicFactory implements ApplicationContextAware, InitializingBean {

    private static ConcurrentMap<String, BLogic> BLOGIC_REPOSITORY = new ConcurrentHashMap<>();

    private static ApplicationContext applicationContext;

    @Override
    public void afterPropertiesSet() throws Exception {
        initBLogicRepository(applicationContext);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    private void initBLogicRepository(ApplicationContext applicationContext) {
        if (applicationContext == null) {
            return;
        }
        Map<String, Object> beanMap = applicationContext.getBeansWithAnnotation(Table.class);
        if (beanMap != null && beanMap.size() > 0) {
            for (Object bean : beanMap.values()) {
                if (bean instanceof BLogic) {
                    String name = bean.getClass().getAnnotation(Table.class).value();
                    BLogic blogic = (BLogic) bean;

                    registerBLogic(name, blogic);
                }
            }
        }
    }

    public static BLogic registerBLogic(String name, BLogic bLogic) {
        log.info(">>>>>>>>>> register BLogic, {}:{}", name, bLogic.getClass().getName());
        return BLOGIC_REPOSITORY.put(name, bLogic);
    }

    public static BLogic loadBLogic(String name) {
        return BLOGIC_REPOSITORY.get(name);
    }
}
