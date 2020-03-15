package study.canal.client.support.canal.dispater;

import com.alibaba.otter.canal.protocol.CanalEntry;
import com.google.common.collect.ImmutableList;
import com.google.protobuf.ByteString;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class EntryDispatcher {

    private static final List<CanalEntry.EntryType> IGNORE_ENTRY_TYPE_LT = ImmutableList.of(
            CanalEntry.EntryType.TRANSACTIONBEGIN,
            CanalEntry.EntryType.TRANSACTIONEND);

    public static void dispatcher(List<CanalEntry.Entry> entryLt) {
        if (entryLt == null) {
            log.info("entry list is empty");
            return;
        }
        for (CanalEntry.Entry entry : entryLt) {
            //entry type
            CanalEntry.EntryType entryType = entry.getEntryType();
            if (IGNORE_ENTRY_TYPE_LT.contains(entryType)) {
                log.info("entry type={}", entryType);
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
                rowDataLt.forEach(data -> {
                    List<CanalEntry.Column> beforeColumnsLt = data.getBeforeColumnsList();
                    List<CanalEntry.Column> afterColumnsLt = data.getAfterColumnsList();
                });
            } catch (Exception ex) {
                log.error("", ex);
            }
        }
    }
}
