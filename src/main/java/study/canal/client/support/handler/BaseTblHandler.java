package study.canal.client.support.handler;

import com.alibaba.otter.canal.protocol.CanalEntry;

import java.util.List;

public abstract class BaseTblHandler implements TblHandler {

    public final void doHandle(List<CanalEntry.Column> beforeColumnLt,
                               List<CanalEntry.Column> afterColumnLt) {
        try {
            whenInsert();

            whenUpdate();

            whenDelete();
        } catch (Exception ex) {

        } finally {

        }
    }
}
