package study.canal.client.support.handler;

import com.alibaba.otter.canal.protocol.CanalEntry;

import java.util.List;

public interface TableHandler {

    default void handleInsert(List<CanalEntry.Column> afterColumnsLt) {
    }

    default void handleDelete(List<CanalEntry.Column> beforeColumnsLt) {
    }

    default void handleUpdate(List<CanalEntry.Column> beforeColumnsLt,
                               List<CanalEntry.Column> afterColumnsLt) {
    }
}
