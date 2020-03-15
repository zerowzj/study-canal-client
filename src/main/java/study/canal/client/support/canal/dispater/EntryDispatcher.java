package study.canal.client.support.canal.dispater;

import com.alibaba.otter.canal.protocol.CanalEntry;
import com.google.protobuf.ByteString;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class EntryDispatcher {

    List<CanalEntry.EntryType> IGNORE_ENTRY_TYPE_LT = null;

    public void dispatcher(List<CanalEntry.Entry> entryLt) {
        if (entryLt == null) {
            log.info("entry list is empty");
            return;
        }
        for (CanalEntry.Entry entry : entryLt) {
            CanalEntry.EntryType entryType = entry.getEntryType();
            if (IGNORE_ENTRY_TYPE_LT.contains(entryType)) {
                log.info("entry_type={}", entryType);
                continue;
            }
            try {
                //row change
                ByteString byteString = entry.getStoreValue();
                CanalEntry.RowChange rowChange = CanalEntry.RowChange.parseFrom(byteString);
                //header
                CanalEntry.Header header = entry.getHeader();
                String logfileName = header.getLogfileName();
                Long logfileOffset = header.getLogfileOffset();
                String schemaName = header.getSchemaName();
                String tableName = header.getTableName();
                //event type
                CanalEntry.EventType eventType = rowChange.getEventType();
                //row data
                List<CanalEntry.RowData> rowDataLt = rowChange.getRowDatasList();

                log.info("binlog[{}:{}], name[{},{}], eventType: {}", logfileName, logfileOffset, schemaName, tableName, eventType);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
