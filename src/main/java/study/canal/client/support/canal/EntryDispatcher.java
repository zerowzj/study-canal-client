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
            log.info("entry list is empty");
            return;
        }
        for (CanalEntry.Entry entry : entryLt) {
            CanalEntry.EntryType entryType = entry.getEntryType();
            if (entryType == CanalEntry.EntryType.TRANSACTIONBEGIN ||
                    entryType == CanalEntry.EntryType.TRANSACTIONEND) {
                log.info("entry_type={}", entryType);
                continue;
            }
            try {
                CanalEntry.RowChange rowChange = CanalEntry.RowChange.parseFrom(entry.getStoreValue());
                CanalEntry.Header header = entry.getHeader();
                header.getSchemaName();
                header.getTableName();
                CanalEntry.EventType eventType = rowChange.getEventType();

                System.out.println(String.format("================ binlog[%s:%s] , name[%s,%s] , eventType : %s",
                        entry.getHeader().getLogfileName(), entry.getHeader().getLogfileOffset(),
                        entry.getHeader().getSchemaName(), entry.getHeader().getTableName(),
                        eventType));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
