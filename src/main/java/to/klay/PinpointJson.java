package to.klay;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author Klayton Killough
 * Date: 6/28/2019
 */

public class PinpointJson {

    @SerializedName("originationNumber")
    @Expose
    private String originationNumber;
    @SerializedName("destinationNumber")
    @Expose
    private String destinationNumber;
    @SerializedName("messageKeyword")
    @Expose
    private String messageKeyword;
    @SerializedName("messageBody")
    @Expose
    private String messageBody;
    @SerializedName("inboundMessageId")
    @Expose
    private String inboundMessageId;
    @SerializedName("previousPublishedMessageId")
    @Expose
    private String previousPublishedMessageId;

    public String getOriginationNumber() {
        return originationNumber;
    }

    public void setOriginationNumber(String originationNumber) {
        this.originationNumber = originationNumber;
    }

    public String getDestinationNumber() {
        return destinationNumber;
    }

    public void setDestinationNumber(String destinationNumber) {
        this.destinationNumber = destinationNumber;
    }

    public String getMessageKeyword() {
        return messageKeyword;
    }

    public void setMessageKeyword(String messageKeyword) {
        this.messageKeyword = messageKeyword;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    public String getInboundMessageId() {
        return inboundMessageId;
    }

    public void setInboundMessageId(String inboundMessageId) {
        this.inboundMessageId = inboundMessageId;
    }

    public String getPreviousPublishedMessageId() {
        return previousPublishedMessageId;
    }

    public void setPreviousPublishedMessageId(String previousPublishedMessageId) {
        this.previousPublishedMessageId = previousPublishedMessageId;
    }

}
