package study.canal.client.support.handler;

import com.alibaba.otter.canal.protocol.CanalEntry;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public abstract class BaseTableHandler implements TableHandler {

    @Override
    public final void doHandle(List<CanalEntry.Column> beforeColumnsLt, List<CanalEntry.Column> afterColumnsLt) {
        try {
            if (beforeColumnsLt == null) {
                onInsert(afterColumnsLt);
            } else if (afterColumnsLt == null) {
                onDelete(beforeColumnsLt);
            } else if (1 == 1) {
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
