package study.canal.client.support.canal.handler;

public abstract class BTableHandler implements TableHandler {


    public void doHandle() {
        try {
            whenInsert();

            whenUpdate();

            whenDelete();
        } catch (Exception ex) {

        } finally {
            
        }
    }
}
