package shoukobot.services.rconClient;

import java.io.*;
import java.net.SocketException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;


public class RconConnection {

    public static final int SERVERDATA_EXECCOMMAND = 2;
    public static final int SERVERDATA_AUTH = 3;

    private int requestId;
    private int type;
    private byte[] payload;

    private RconConnection(int requestId, int type, byte[] payload) {
        this.requestId = requestId;
        this.type = type;
        this.payload = payload;
    }

    public int getRequestId() {
        return requestId;
    }

    public int getType() {
        return type;
    }

    public byte[] getPayload() {
        return payload;
    }


    protected static RconPacket send(RconClient rcon, int type, byte[] payload) throws IOException {
        try {
            RconConnection.write(rcon.getSocket().getOutputStream(), new RconPacket(rcon.getRequestId(), type, payload));
        }
        catch(SocketException se) {
            rcon.getSocket().close();

            throw se;
        }

        return RconConnection.read(rcon.getSocket().getInputStream());
    }

    private static void write(OutputStream out, RconPacket packet) throws IOException {

        ByteBuffer buffer = ByteBuffer.allocate(packet.getPacketLength());
        buffer.order(ByteOrder.LITTLE_ENDIAN);

        buffer.putInt(packet.getPayloadLength());
        buffer.putInt(packet.getRequestId());
        buffer.putInt(packet.getType());
        buffer.put(packet.getPayload());

        buffer.put((byte)0);
        buffer.put((byte)0);

        out.write(buffer.array());
        out.flush();
    }

    private static RconPacket read(InputStream in) throws IOException {
        byte[] header = new byte[4 * 3];

        in.read(header);

        try {
            ByteBuffer buffer = ByteBuffer.wrap(header);
            buffer.order(ByteOrder.LITTLE_ENDIAN);

            int length = buffer.getInt();
            int requestId = buffer.getInt();
            int type = buffer.getInt();

            byte[] payload = new byte[length - 4 - 4 - 2];

            DataInputStream dis = new DataInputStream(in);

            dis.readFully(payload);

            dis.read(new byte[2]);

            return new RconPacket(requestId, type, payload);
        }
        catch(BufferUnderflowException | EOFException e) {
            throw new IOException("Cannot read the whole packet");
        }
    }

}
