package study.canal.client.support.canal;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.common.utils.AddressUtils;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.Message;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Setter
@Getter
@Slf4j
public class CanalClient {

    private volatile boolean running = true;

    private int port = 11111;

    private String destination;

    private String username = "";

    private String password = "";

    private String filter = ".*\\..*";

    private int batchSize = 100;

    private static ConcurrentMap<String, TableHandler> tableHandlerRepository = new ConcurrentHashMap<>();

    public static TableHandler registerTableHandler(String name, TableHandler tableHandler) {
        log.info(">>>>>>>>>>> xxl-job register jobhandler success, name:{}, jobHandler:{}", name, tableHandler);
        return tableHandlerRepository.put(name, tableHandler);
    }

    public static TableHandler loadTableHandler(String name) {
        return tableHandlerRepository.get(name);
    }

    /**
     *
     */
    public void start() {
        //
        InetSocketAddress address = new InetSocketAddress(AddressUtils.getHostIp(), port);
        //
        CanalConnector connector = CanalConnectors.newSingleConnector(address, "example", username, password);
        try {
            //
            connector.connect();
            connector.subscribe(filter);
            connector.rollback();
            //
            while (running) {
                Message message = connector.getWithoutAck(batchSize);
                int size = message.getEntries().size();
                long batchId = message.getId();
                if (batchId == -1 || size == 0) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    log.info("batch_id={}, size={}", batchId, size);
                    new EntryDispatcher().dispatcher(message.getEntries());
                }
                //提交确认
                connector.ack(batchId);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            // connector.rollback(batchId); // 处理失败, 回滚数据
        }
    }

    public void destroy() {

    }

    private static void printEntry(List<CanalEntry.Entry> entrys) {
        for (CanalEntry.Entry entry : entrys) {
            if (entry.getEntryType() == CanalEntry.EntryType.TRANSACTIONBEGIN ||
                    entry.getEntryType() == CanalEntry.EntryType.TRANSACTIONEND) {
                continue;
            }

            CanalEntry.RowChange rowChage;
            try {
                rowChage = CanalEntry.RowChange.parseFrom(entry.getStoreValue());
            } catch (Exception e) {
                throw new RuntimeException("ERROR ## parser of eromanga-event has an error , data:" + entry.toString(),
                        e);
            }

            CanalEntry.EventType eventType = rowChage.getEventType();
            System.out.println(String.format("================ binlog[%s:%s] , name[%s,%s] , eventType : %s",
                    entry.getHeader().getLogfileName(), entry.getHeader().getLogfileOffset(),
                    entry.getHeader().getSchemaName(), entry.getHeader().getTableName(),
                    eventType));

            for (CanalEntry.RowData rowData : rowChage.getRowDatasList()) {
                if (eventType == CanalEntry.EventType.DELETE) {
                    printColumn(rowData.getBeforeColumnsList());
                } else if (eventType == CanalEntry.EventType.INSERT) {
                    printColumn(rowData.getAfterColumnsList());
                } else {
                    System.out.println("------- before");
                    printColumn(rowData.getBeforeColumnsList());
                    System.out.println("------- after");
                    printColumn(rowData.getAfterColumnsList());
                }
            }
        }
    }

    private static void printColumn(List<CanalEntry.Column> columns) {
        for (CanalEntry.Column column : columns) {
            System.out.println(column.getName() + " : " + column.getValue() + "    update=" + column.getUpdated());
        }
    }

    public static void main(String[] args) {
        new CanalClient().start();
    }
}
