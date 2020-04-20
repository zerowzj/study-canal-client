package study.canal.client.support.handler;

import com.alibaba.otter.canal.protocol.CanalEntry;

import java.util.List;

public abstract class BaseTblHandler implements TblHandler {

    public final void doHandle(List<CanalEntry.Column> beforeColumnsLt,
                               List<CanalEntry.Column> afterColumnsLt) {
        try {
            if (1 == 1) {
                whenInsert();
            } else if (1 == 1) {
                whenDelete();
            } else if (1 == 1) {
                whenUpdate();
            }
        } catch (Exception ex) {
        } finally {
        }
    }
}
