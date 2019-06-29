package to.klay;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author Klayton Killough
 * Date: 6/28/2019
 */
public class SNSJSon {

    @SerializedName("Type")
    @Expose
    private String type;
    @SerializedName("MessageId")
    @Expose
    private String messageId;
    @SerializedName("TopicArn")
    @Expose
    private String topicArn;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("Timestamp")
    @Expose
    private String timestamp;
    @SerializedName("SignatureVersion")
    @Expose
    private String signatureVersion;
    @SerializedName("Signature")
    @Expose
    private String signature;
    @SerializedName("SigningCertURL")
    @Expose
    private String signingCertURL;
    @SerializedName("UnsubscribeURL")
    @Expose
    private String unsubscribeURL;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getTopicArn() {
        return topicArn;
    }

    public void setTopicArn(String topicArn) {
        this.topicArn = topicArn;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSignatureVersion() {
        return signatureVersion;
    }

    public void setSignatureVersion(String signatureVersion) {
        this.signatureVersion = signatureVersion;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getSigningCertURL() {
        return signingCertURL;
    }

    public void setSigningCertURL(String signingCertURL) {
        this.signingCertURL = signingCertURL;
    }

    public String getUnsubscribeURL() {
        return unsubscribeURL;
    }

    public void setUnsubscribeURL(String unsubscribeURL) {
        this.unsubscribeURL = unsubscribeURL;
    }

}