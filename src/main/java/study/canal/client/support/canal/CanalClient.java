package study.canal.client.support.canal;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.common.utils.AddressUtils;
import com.alibaba.otter.canal.protocol.Message;

import java.net.InetSocketAddress;

public class CanalClient {

    private volatile boolean running = true;

    public void connector() {
        InetSocketAddress address = new InetSocketAddress(AddressUtils.getHostIp(), 1111);
        // 创建链接
        CanalConnector connector = CanalConnectors.newSingleConnector(address, "example", "", "");
        int batchSize = 1000;
        try {
            connector.connect();
            connector.subscribe(".*\\..*");
            connector.rollback();
            while (running) {
                Message message = connector.getWithoutAck(batchSize);
                int size = message.getEntries().size();
                long batchId = message.getId();
                if (batchId == -1 || size == 0) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
//                    printEntry(message.getEntries());
                }

                connector.ack(batchId); // 提交确认
                // connector.rollback(batchId); // 处理失败, 回滚数据
            }
        } catch (Exception ex) {

        }
    }

    public void shutdown() {
        running = false;
    }
}
