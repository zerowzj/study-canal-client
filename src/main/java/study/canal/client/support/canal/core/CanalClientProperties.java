package study.canal.client.support.canal.core;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CanalClientProperties {

    private Server server;

    private Client client;

    @Setter
    @Getter
    static class Server {

        private int port = 11111;
    }

    @Setter
    @Getter
    static class Client {

        int batchSize = 100;
    }
}
