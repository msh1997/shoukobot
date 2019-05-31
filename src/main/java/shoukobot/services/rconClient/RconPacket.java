package shoukobot.services.rconClient;

public class RconPacket {
    private int requestId;
    private int type;
    private byte[] payload;
    private int packetLength;
    private int payloadLength;

    public RconPacket(int requestId, int type, byte[] payload) {
        this.requestId = requestId;
        this.type = type;
        this.payload = payload;
        this.payloadLength = 4 + 4 + payload.length + 2;
        this.packetLength = payloadLength + 4;
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

    public int getPacketLength() {
        return packetLength;
    }

    public int getPayloadLength() {
        return payloadLength;
    }
}
