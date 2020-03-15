package study.canal.client.support.canal;

import com.alibaba.otter.canal.protocol.CanalEntry;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class CanalEntryDispatcher {

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
                //header
                CanalEntry.Header header = entry.getHeader();
                String logfileName = header.getLogfileName();
                Long logfileOffset = header.getLogfileOffset();
                String schemaName = header.getSchemaName();
                String tableName = header.getTableName();
                //event type
                CanalEntry.EventType eventType = rowChange.getEventType();
                log.info("binlog[{}:{}], name[{},{}], eventType: {}", logfileName, logfileOffset, schemaName, tableName, eventType);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
