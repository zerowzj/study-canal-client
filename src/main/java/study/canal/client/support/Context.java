package study.canal.client.support;

import com.alibaba.otter.canal.protocol.CanalEntry;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Context {

    private String schemaName;

    private String tableName;

    private CanalEntry.EventType eventType;

    public Context(String schemaName, String tableName, CanalEntry.EventType eventType) {
        this.schemaName = schemaName;
        this.tableName = tableName;
        this.eventType = eventType;
    }
}
