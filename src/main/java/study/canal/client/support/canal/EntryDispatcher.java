package study.canal.client.support.canal;

import com.alibaba.otter.canal.protocol.CanalEntry;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class EntryDispatcher {

    /**
     * 分发器
     */
    public void dispatcher(List<CanalEntry.Entry> entryLt) {
        if (entryLt == null) {
            return;
        }
        for (CanalEntry.Entry entry : entryLt) {
            CanalEntry.EntryType entryType = entry.getEntryType();
            if (entryType == CanalEntry.EntryType.TRANSACTIONBEGIN ||
                    entryType == CanalEntry.EntryType.TRANSACTIONEND) {
                log.info("");
                continue;
            }
            try {
                CanalEntry.RowChange rowChange = CanalEntry.RowChange.parseFrom(entry.getStoreValue());
                CanalEntry.EventType eventType = rowChange.getEventType();
                CanalEntry.Header header = entry.getHeader();

                System.out.println(String.format("================ binlog[%s:%s] , name[%s,%s] , eventType : %s",
                        entry.getHeader().getLogfileName(), entry.getHeader().getLogfileOffset(),
                        entry.getHeader().getSchemaName(), entry.getHeader().getTableName(),
                        eventType));
            } catch (Exception ex) {

            }
        }
    }
}
