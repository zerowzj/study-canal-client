package study.canal.client.support.canal;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.common.utils.AddressUtils;
import com.alibaba.otter.canal.protocol.Message;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

@Slf4j
public class CanalClient {

    private volatile boolean running = true;

    public void connect() {
        InetSocketAddress address = new InetSocketAddress(AddressUtils.getHostIp(), 1111);
        // 创建链接
        CanalConnector connector = CanalConnectors.newSingleConnector(address, "example", "", "");
        try {
            connector.connect();
            connector.subscribe(".*\\..*");
            connector.rollback();

            while (running) {
                Message message = connector.getWithoutAck(100);
                int size = message.getEntries().size();
                long batchId = message.getId();
                if (batchId == -1 || size == 0) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    message.getEntries();
//                    printEntry(message.getEntries());
                }
                //提交确认
                connector.ack(batchId);
            }
        } catch (Exception ex) {
            // connector.rollback(batchId); // 处理失败, 回滚数据
        }
    }

    public void shutdown() {
        running = false;
    }
}
