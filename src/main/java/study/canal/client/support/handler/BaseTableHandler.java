package study.canal.client.support.handler;

import com.alibaba.otter.canal.protocol.CanalEntry;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public abstract class BaseTableHandler implements TableHandler {

    public final void doHandle(List<CanalEntry.Column> beforeColumnsLt,
                               List<CanalEntry.Column> afterColumnsLt) {
        try {
            if (beforeColumnsLt == null) {
                handleInsert(afterColumnsLt);
            } else if (afterColumnsLt == null) {
                handleDelete(beforeColumnsLt);
            } else if (1 == 1) {
                handleUpdate(beforeColumnsLt, afterColumnsLt);
            }
        } catch (Exception ex) {
        } finally {
        }
    }
}
