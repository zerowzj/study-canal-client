package study.canal.client.support.handler;

import com.alibaba.otter.canal.protocol.CanalEntry;
import lombok.extern.slf4j.Slf4j;
import study.canal.client.support.EntryContext;

import java.util.List;

@Slf4j
public abstract class BaseTableHandler implements TableHandler {

    @Override
    public final void doHandle(EntryContext entryContext,
                               List<CanalEntry.Column> beforeColumnsLt,
                               List<CanalEntry.Column> afterColumnsLt) {
        try {
            CanalEntry.EventType eventType = entryContext.getEventType();
            if (eventType == CanalEntry.EventType.INSERT) {
                onInsert(afterColumnsLt);
            } else if (eventType == CanalEntry.EventType.DELETE) {
                onDelete(beforeColumnsLt);
            } else if (eventType == CanalEntry.EventType.UPDATE) {
                onUpdate(beforeColumnsLt, afterColumnsLt);
            }
        } catch (Exception ex) {
            log.error("", ex);
        } finally {
            log.info("");
        }
    }

    /**
     * 新增时
     *
     * @param entryContext
     * @param afterColumnsLt
     */
    protected void onInsert(List<CanalEntry.Column> afterColumnsLt) {
    }

    /**
     * 删除时
     *
     * @param entryContext
     * @param beforeColumnsLt
     */
    protected void onDelete(List<CanalEntry.Column> beforeColumnsLt) {
    }

    /**
     * 更新时
     *
     * @param entryContext
     * @param beforeColumnsLt
     * @param afterColumnsLt
     */
    protected void onUpdate(List<CanalEntry.Column> beforeColumnsLt, List<CanalEntry.Column> afterColumnsLt) {
    }
}
