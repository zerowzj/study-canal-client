package study.canal.client.support.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import study.canal.client.support.utils.SpringContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Slf4j
@Configuration
public class TableHandlerFactory implements InitializingBean {

    @Override
    public void afterPropertiesSet() throws Exception {
        initTableHandlerRepository();
    }

    private void initTableHandlerRepository() {
        ApplicationContext applicationContext = SpringContext.getApplicationContext();
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

    private static ConcurrentMap<String, TableHandler> HANDLER_REPOSITORY = new ConcurrentHashMap<>();

    public static TableHandler registerTableHandler(String name, TableHandler jobHandler){
        log.info(">>>>>>>>>>> xxl-job register handler success, name:{}, jobHandler:{}", name, jobHandler);
        return HANDLER_REPOSITORY.put(name, jobHandler);
    }

    public static TableHandler loadJobHandler(String name){
        return HANDLER_REPOSITORY.get(name);
    }
}
