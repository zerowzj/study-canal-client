package study.canal.client.support.handler;

import com.alibaba.otter.canal.protocol.CanalEntry;
import study.canal.client.support.EntryContext;

import java.util.List;

public interface TableHandler {

    void doHandle(EntryContext context, List<CanalEntry.Column> beforeColumnsLt, List<CanalEntry.Column> afterColumnsLt);
}
