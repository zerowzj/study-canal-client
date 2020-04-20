package study.canal.client.support.handler;

import com.alibaba.otter.canal.protocol.CanalEntry;

import java.util.List;

public interface TableHandler {

    void doHandle(List<CanalEntry.Column> beforeColumnsLt, List<CanalEntry.Column> afterColumnsLt);
}
