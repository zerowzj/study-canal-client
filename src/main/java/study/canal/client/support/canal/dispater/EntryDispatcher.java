package study.canal.client.support.canal.dispater;

import com.alibaba.otter.canal.protocol.CanalEntry;
import com.google.common.collect.ImmutableList;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.google.protobuf.ByteString;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
public class EntryDispatcher {

    private static final List<CanalEntry.EntryType> IGNORE_ENTRY_TYPE_LT = ImmutableList.of(
            CanalEntry.EntryType.TRANSACTIONBEGIN,
            CanalEntry.EntryType.TRANSACTIONEND);

    private static ThreadFactory THREAD_FACTORY = new ThreadFactoryBuilder()
            .setNameFormat("pool-handler-thread-%d")
            .build();

    private static ThreadPoolExecutor POOL = new ThreadPoolExecutor(10, 10,
            0, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(100),
            THREAD_FACTORY);

    /**
     * 分发
     *
     * @param entryLt
     */
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
                POOL.submit(() -> {
                    log.info("binlog[{}:{}], name[{},{}], eventType: {}", logfileName, logfileOffset, schemaName, tableName, eventType);
                    rowDataLt.forEach(data -> {
                        List<CanalEntry.Column> beforeColumnsLt = data.getBeforeColumnsList();
                        List<CanalEntry.Column> afterColumnsLt = data.getAfterColumnsList();
                    });
                });

            } catch (Exception ex) {
                log.error("", ex);
            }
        }
    }

    /**
     * 清理
     */
    public static void destroy() {
        if (!Objects.isNull(POOL)) {
            POOL.shutdown();
        }
    }
}
