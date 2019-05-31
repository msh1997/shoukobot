package shoukobot.services.rconClient;


import java.io.IOException;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.Random;

public class RconClient {

    private final Object sync = new Object();
    private final Random rand = new Random();

    private int requestId;
    private Socket socket;

    public RconClient(String host, int port, byte[] password) throws IOException{

        this.connect(host, port, password);
    }

    public void connect(String host, int port, byte[] password) throws IOException {

        if(host == null || host.trim().isEmpty()) {
            throw new IllegalArgumentException("Host can't be null or empty");
        }

        if(port < 1 || port > 65535) {
            throw new IllegalArgumentException("Port is out of range");
        }

        synchronized(sync) {
            this.requestId = rand.nextInt();
            this.socket = new Socket(host, port);
        }

        RconPacket res = this.send(RconConnection.SERVERDATA_AUTH, password);
        if(res.getRequestId() == -1) {
            throw new IOException("Password rejected by server");
        }
    }

    public void disconnect() throws IOException {
        synchronized(sync) {
            this.socket.close();
        }
    }

    public String sendCommand(String payload) throws IOException {
        if(payload == null || payload.trim().isEmpty()) {
            throw new IllegalArgumentException("Payload can't be null or empty");
        }

        RconPacket response = this.send(RconConnection.SERVERDATA_EXECCOMMAND, payload.getBytes());

        return new String(response.getPayload(), Charset.forName("UTF-8"));
    }

    private RconPacket send(int type, byte[] payload) throws IOException {
        synchronized(sync) {
            return RconConnection.send(this, type, payload);
        }
    }

    public int getRequestId() {
        return requestId;
    }

    public Socket getSocket() {
        return socket;
    }

}
