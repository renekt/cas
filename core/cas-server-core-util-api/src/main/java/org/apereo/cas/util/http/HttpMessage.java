package org.apereo.cas.util.http;

import lombok.extern.slf4j.Slf4j;
import org.apereo.cas.util.EncodingUtils;
import org.springframework.http.MediaType;
import java.io.Serializable;
import java.net.URL;
import lombok.ToString;
import lombok.Getter;

/**
 * This is {@link HttpMessage}.
 *
 * @author Misagh Moayyed
 * @since 5.0.0
 */
@Slf4j
@ToString
@Getter
public class HttpMessage implements Serializable {

    private static final long serialVersionUID = 2015460875654586133L;

    /**
     * The default asynchronous callbacks enabled.
     */
    private static final boolean DEFAULT_ASYNCHRONOUS_CALLBACKS_ENABLED = true;

    private final URL url;

    private final String message;

    private int responseCode;

    /**
     * Whether this message should be sent in an asynchronous fashion.
     * Default is true.
     **/
    private final boolean asynchronous;

    /**
     * The content type for this message once submitted.
     * Default is {@link MediaType#APPLICATION_FORM_URLENCODED}.
     **/
    private String contentType = MediaType.APPLICATION_FORM_URLENCODED_VALUE;

    /**
     * Prepare the sender with a given url and the message to send.
     *
     * @param url     the url to which the message will be sent.
     * @param message the message itself.
     */
    public HttpMessage(final URL url, final String message) {
        this(url, message, DEFAULT_ASYNCHRONOUS_CALLBACKS_ENABLED);
    }

    /**
     * Prepare the sender with a given url and the message to send.
     *
     * @param url     the url to which the message will be sent.
     * @param message the message itself.
     * @param async   whether the message should be sent asynchronously.
     */
    public HttpMessage(final URL url, final String message, final boolean async) {
        this.url = url;
        this.message = message;
        this.asynchronous = async;
    }

    public boolean isAsynchronous() {
        return this.asynchronous;
    }

    public URL getUrl() {
        return this.url;
    }

    public String getMessage() {
        return this.formatOutputMessageInternal(this.message);
    }

    public void setContentType(final String type) {
        this.contentType = type;
    }

    public int getResponseCode() {
        return this.responseCode;
    }

    /**
     * Encodes the message in UTF-8 format in preparation to send.
     *
     * @param message Message to format and encode
     * @return The encoded message.
     */
    protected String formatOutputMessageInternal(final String message) {
        try {
            return EncodingUtils.urlEncode(message);
        } catch (final Exception e) {
            LOGGER.warn("Unable to encode URL", e);
        }
        return message;
    }

    public void setResponseCode(final int responseCode) {
        this.responseCode = responseCode;
    }
}
