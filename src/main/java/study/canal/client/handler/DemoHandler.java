package study.canal.client.handler;

import com.alibaba.otter.canal.protocol.CanalEntry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import study.canal.client.support.handler.BaseTableHandler;
import study.canal.client.support.handler.Table;

import java.util.List;

@Slf4j
@Component
@Table("order_base")
public class DemoHandler extends BaseTableHandler {

    @Override
    protected void onInsert(List<CanalEntry.Column> afterColumnsLt) {
        log.info("ffffffffffffffffffffffffdsa");
    }

    @Override
    protected void onDelete(List<CanalEntry.Column> beforeColumnsLt) {
        beforeColumnsLt.forEach(e -> {
        });
        log.info("ffffffffffffffffffffffffdsa");
    }
}
