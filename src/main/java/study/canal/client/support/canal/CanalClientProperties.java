package study.canal.client.support.canal;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class CanalClientProperties {

    private Server server;

    private Client client;

    @Setter
    @Getter
    @ToString
    static class Server {

        private int port = 11111;
    }

    @Setter
    @Getter
    @ToString
    static class Client {

        int batchSize = 100;
    }
}
