package study.canal.client.handler;

import com.alibaba.otter.canal.protocol.CanalEntry;
import org.springframework.stereotype.Component;
import study.canal.client.support.handler.BaseTableHandler;
import study.canal.client.support.handler.Table;

import java.util.List;

@Component
@Table("")
public class DemoHandler extends BaseTableHandler {

    @Override
    protected void handleInsert(List<CanalEntry.Column> afterColumnsLt) {
        super.handleInsert(afterColumnsLt);
    }

    @Override
    protected void handleDelete(List<CanalEntry.Column> beforeColumnsLt) {
        super.handleDelete(beforeColumnsLt);
    }
}
