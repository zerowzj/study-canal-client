package study.canal.client.support.blogic;

import com.alibaba.otter.canal.protocol.CanalEntry;
import study.canal.client.support.EntryContext;

import java.util.List;

public interface BLogic {

    void processBLogic(EntryContext context, List<CanalEntry.Column> beforeColumnsLt, List<CanalEntry.Column> afterColumnsLt);
}
