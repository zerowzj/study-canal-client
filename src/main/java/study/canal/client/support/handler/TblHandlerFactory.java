package study.canal.client.support.handler;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import study.canal.client.support.SpringContext;

public class TblHandlerFactory implements InitializingBean {


    @Override
    public void afterPropertiesSet() throws Exception {


    }

    private void initTblHandlerRepository(){
        ApplicationContext applicationContext = SpringContext.getApplicationContext();
        if (applicationContext == null) {
            return;
        }
    }

}
