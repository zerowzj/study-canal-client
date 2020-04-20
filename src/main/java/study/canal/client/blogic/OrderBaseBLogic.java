package study.canal.client.blogic;

import com.alibaba.otter.canal.protocol.CanalEntry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import study.canal.client.support.blogic.BaseBLogic;
import study.canal.client.support.blogic.Table;

import java.util.List;

@Slf4j
@Component
@Table("order_base")
public class OrderBaseBLogic extends BaseBLogic {

    @Override
    protected void onInsert(List<CanalEntry.Column> afterColumnsLt) {
        log.info("onInsertonInsert");
        afterColumnsLt.forEach(e -> {
            log.info("name={}, value={}, is_updated={}", e.getName(), e.getValue(), e.hasUpdated());
        });
    }

    @Override
    protected void onDelete(List<CanalEntry.Column> beforeColumnsLt) {
        beforeColumnsLt.forEach(e -> {

        });
        log.info("onDeleteonDelete");
    }

    @Override
    protected void onUpdate(List<CanalEntry.Column> beforeColumnsLt, List<CanalEntry.Column> afterColumnsLt) {
        log.info("onUpdateonUpdate");
        afterColumnsLt.forEach(e -> {
            if (e.getUpdated()) {
                log.info("[{}:{}], {}", e.getName(), e.getValue(),e.hasUpdated());
            }
        });
    }
}
