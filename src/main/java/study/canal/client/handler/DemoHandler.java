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
    protected void onInsert(List<CanalEntry.Column> afterColumnsLt) {
        super.onInsert(afterColumnsLt);
    }

    @Override
    protected void onDelete(List<CanalEntry.Column> beforeColumnsLt) {
        super.onDelete(beforeColumnsLt);
    }
}
