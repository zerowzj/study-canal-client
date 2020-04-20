package study.canal.client.support.handler;

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
public class TableHandlerFactory implements ApplicationContextAware, InitializingBean {

    private static ConcurrentMap<String, TableHandler> HANDLER_REPOSITORY = new ConcurrentHashMap<>();

    private static ApplicationContext applicationContext;

    @Override
    public void afterPropertiesSet() throws Exception {
        initTableHandlerRepository(applicationContext);
    }

    private void initTableHandlerRepository(ApplicationContext applicationContext) {
        if (applicationContext == null) {
            return;
        }
        Map<String, Object> beanMap = applicationContext.getBeansWithAnnotation(Table.class);
        if (beanMap != null && beanMap.size() > 0) {
            for (Object bean : beanMap.values()) {
                if (bean instanceof TableHandler) {
                    String name = bean.getClass().getAnnotation(Table.class).value();
                    TableHandler handler = (TableHandler) bean;

                    registerTableHandler(name, handler);
                }
            }
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public static TableHandler registerTableHandler(String name, TableHandler tableHandler) {
        log.info(">>>>>>>>>> register handler success, {}:{}", name, tableHandler.getClass().getName());
        return HANDLER_REPOSITORY.put(name, tableHandler);
    }

    public static TableHandler loadTableHandler(String name) {
        return HANDLER_REPOSITORY.get(name);
    }
}
