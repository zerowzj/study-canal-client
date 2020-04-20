package study.canal.client.support.canal.dispater;

import com.alibaba.otter.canal.protocol.CanalEntry;
import com.google.common.collect.ImmutableList;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.google.protobuf.ByteString;
import lombok.extern.slf4j.Slf4j;
import study.canal.client.support.blogic.BLogic;
import study.canal.client.support.blogic.BLogicFactory;
import study.canal.client.support.canal.EntryContext;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
public class EntryDispatcher {

    private static final List<CanalEntry.EntryType> ENTRY_TYPE_IGNORE_LT = ImmutableList.of(
            CanalEntry.EntryType.TRANSACTIONBEGIN,
            CanalEntry.EntryType.TRANSACTIONEND);

    private static ThreadFactory THREAD_FACTORY = new ThreadFactoryBuilder()
            .setNameFormat("POOL-BLOGIC-THREAD-%d")
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
    public static void dispatch(List<CanalEntry.Entry> entryLt) {
        if (entryLt == null) {
            log.warn("entry list is empty");
            return;
        }
        for (CanalEntry.Entry entry : entryLt) {
            try {
                //（★）实体类型
                CanalEntry.EntryType entryType = entry.getEntryType();
                log.info("entry_type={}", entryType);
                if (ENTRY_TYPE_IGNORE_LT.contains(entryType)) {
                    log.warn("11");
                    continue;
                }

                //（★）实体头部
                CanalEntry.Header header = entry.getHeader();
                String logfileName = header.getLogfileName();
                Long logfileOffset = header.getLogfileOffset();
                String schemaName = header.getSchemaName();
                String tableName = header.getTableName();

                //（★）实体存储值，即行变化：事件类型、行数据
                ByteString byteString = entry.getStoreValue();
                CanalEntry.RowChange rowChange = CanalEntry.RowChange.parseFrom(byteString);
                //事件类型
                CanalEntry.EventType eventType = rowChange.getEventType();
                log.info("bin_log[{}:{}], name[{}:{}], event_type[{}]", logfileName, logfileOffset, schemaName, tableName, eventType);
                //行数据列表
                List<CanalEntry.RowData> rowDataLt = rowChange.getRowDatasList();

                BLogic bLogic = BLogicFactory.loadBLogic(tableName);
                if(bLogic == null){
                    log.warn("not found BLogic of table[{}]", tableName);
                    continue;
                }
                EntryContext entryContext = new EntryContext(schemaName, tableName, eventType);
                rowDataLt.forEach(data -> {
                    List<CanalEntry.Column> beforeColumnsLt = data.getBeforeColumnsList();
                    List<CanalEntry.Column> afterColumnsLt = data.getAfterColumnsList();
                    bLogic.processBLogic(entryContext, beforeColumnsLt, afterColumnsLt);
                });
            } catch (Exception ex) {
                log.error("", ex);
            }
        }
    }

    /**
     * 销毁
     */
    public static void destroy() {
        if (!Objects.isNull(POOL)) {
            POOL.shutdown();
        }
    }
}
