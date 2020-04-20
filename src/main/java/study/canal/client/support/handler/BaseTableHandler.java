package study.canal.client.support.handler;

import com.alibaba.otter.canal.protocol.CanalEntry;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public abstract class BaseTableHandler implements TableHandler {

    @Override
    public final void doHandle(CanalEntry.EventType eventType, List<CanalEntry.Column> beforeColumnsLt, List<CanalEntry.Column> afterColumnsLt) {
        try {
            if (eventType == CanalEntry.EventType.INSERT) {
                onInsert(afterColumnsLt);
            } else if (eventType == CanalEntry.EventType.DELETE) {
                onDelete(beforeColumnsLt);
            } else if (eventType == CanalEntry.EventType.UPDATE) {
                onUpdate(beforeColumnsLt, afterColumnsLt);
            }
        } catch (Exception ex) {

        } finally {
            log.info("");
        }
    }


    /**
     * 新增时
     *
     * @param afterColumnsLt
     */
    protected void onInsert(List<CanalEntry.Column> afterColumnsLt) {
    }

    /**
     * 删除时
     *
     * @param beforeColumnsLt
     */
    protected void onDelete(List<CanalEntry.Column> beforeColumnsLt) {
    }

    /**
     * 更新时
     *
     * @param beforeColumnsLt
     * @param afterColumnsLt
     */
    protected void onUpdate(List<CanalEntry.Column> beforeColumnsLt, List<CanalEntry.Column> afterColumnsLt) {
    }
}
