package study.canal.client.support.canal.handler;

public abstract class BTableHandler implements TableHandler {


    public void doHandle() {

        try {
            whenUpdate();

            whenInsert();

            whenUpdate();
        } catch (Exception ex) {

        }
    }
}
