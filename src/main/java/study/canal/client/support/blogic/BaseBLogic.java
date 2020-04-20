package study.canal.client.support.blogic;

import com.alibaba.otter.canal.protocol.CanalEntry;
import lombok.extern.slf4j.Slf4j;
import study.canal.client.support.canal.EntryContext;

import java.util.List;

@Slf4j
public abstract class BaseBLogic implements BLogic {

    @Override
    public final void processBLogic(EntryContext entryContext,
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

    protected void onInsert(List<CanalEntry.Column> afterColumnsLt) {
    }

    protected void onDelete(List<CanalEntry.Column> beforeColumnsLt) {
    }

    protected void onUpdate(List<CanalEntry.Column> beforeColumnsLt, List<CanalEntry.Column> afterColumnsLt) {
    }
}
