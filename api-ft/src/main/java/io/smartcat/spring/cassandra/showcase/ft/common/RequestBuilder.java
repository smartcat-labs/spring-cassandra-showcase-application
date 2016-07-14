package io.smartcat.spring.cassandra.showcase.ft.common;

import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.filter.LoggingFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

import cucumber.api.spring.context.SpringApplicationContextBridge;
import io.smartcat.spring.cassandra.showcase.ft.common.serialization.ObjectMapperProvider;
import io.smartcat.spring.cassandra.showcase.ft.util.config.server.ServerConfig;

abstract public class RequestBuilder<T> {

    private final static Logger LOGGER = LoggerFactory.getLogger(RequestBuilder.class);

    private String entryPoint;
    private Client client;
    private final Map<String, String> headers;
    private final Map<String, Object> bodyParameters;
    private final Map<String, Object> queryParameters;
    private final ServerConfig serverConfig = SpringApplicationContextBridge.getApplicationContext()
        .getBean(ServerConfig.class);
    private HttpMethod httpMethod;
    private String endPoint;
    private String bodyText;

    public RequestBuilder() {
        try {
            this.client = buildClient();
        } catch (final Exception e) {
            LOGGER.error("Error creating HTTP client:" + e.getMessage());
        }
        this.entryPoint = serverConfig.getApiEntryPoint();
        this.headers = new HashMap<>();
        this.bodyParameters = new HashMap<>();
        this.bodyText = "";
        this.queryParameters = new HashMap<>();

        withHttpMethod(HttpMethod.GET);
    }

    public T withHttpMethod(final HttpMethod httpMethod) {
        this.httpMethod = httpMethod;

        return getThis();
    }

    public T withHttpHeader(final String name, final String value) {
        headers.put(name, value);

        return getThis();
    }

    public T withContentType(final String contentType) {
        withHttpHeader(HttpHeader.CONTENT_TYPE, contentType);

        return getThis();
    }

    public T withEntryPoint(final String entryPoint) {
        this.entryPoint = entryPoint;

        return getThis();
    }

    public T withEndPoint(final String endPoint) {
        this.endPoint = endPoint;

        return getThis();
    }

    public Invocation build() {
        final WebTarget target = createTarget();

        final Invocation.Builder requestBuilder = target.request();
        addHeadersTo(requestBuilder);

        return bodyParameters.isEmpty()
            ? (bodyText.isEmpty()
                ? buildWithoutBody(requestBuilder)
                : buildWithTextBody(requestBuilder))
            : buildWithBody(requestBuilder);
    }

    protected T withBodyParameter(final String name, final Object value) {
        bodyParameters.put(name, value);

        return getThis();
    }

    protected T withBodyText(final String bodyText) {
        this.bodyText = bodyText;

        return getThis();
    }

    protected T withQueryParameter(final String name, final Object value) {
        queryParameters.put(name, value);

        return getThis();
    }

    protected abstract T getThis();

    protected enum HttpMethod { GET, POST, PUT, DELETE }

    private Client buildClient() throws GeneralSecurityException {
        final ClientConfig clientConfig = new ClientConfig();
        clientConfig.register(JacksonJsonProvider.class);
        clientConfig.register(new ObjectMapperProvider());
        clientConfig.register(
            new LoggingFilter(java.util.logging.Logger.getLogger("jersey-client"), true));

        final SSLContext sslcontext = configureSslContext();
        return ClientBuilder.newBuilder().sslContext(sslcontext).hostnameVerifier((s1, s2) -> true)
            .withConfig(clientConfig).build();
    }

    private SSLContext configureSslContext() throws GeneralSecurityException {
        final SSLContext sslcontext = SSLContext.getInstance("TLS");
        sslcontext.init(null, trustAllManager, new SecureRandom());
        return sslcontext;
    }

    TrustManager[] trustAllManager = new TrustManager[] { new X509TrustManager() {
        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        @Override
        public void checkServerTrusted(final X509Certificate[] chain, final String authType)
            throws CertificateException {
        }

        @Override
        public void checkClientTrusted(final X509Certificate[] chain, final String authType)
            throws CertificateException {
        }
    } };

    private void addHeadersTo(final Invocation.Builder requestBuilder) {
        headers.forEach(requestBuilder::header);
    }

    private WebTarget createTarget() {
        WebTarget target = client.target(String.format("%s/%s", entryPoint, endPoint));

        for (final Map.Entry<String, Object> param : queryParameters.entrySet()) {
            target = target.queryParam(param.getKey(), param.getValue());
        }

        return target;
    }

    private Invocation buildWithBody(final Invocation.Builder requestBuilder) {
        return requestBuilder.build(
            httpMethod.toString(), Entity.entity(bodyParameters, MediaType.APPLICATION_JSON));
    }

    private Invocation buildWithTextBody(final Invocation.Builder requestBuilder) {
        return requestBuilder.build(httpMethod.toString(),
            Entity.entity(bodyText, MediaType.TEXT_PLAIN));
    }

    private Invocation buildWithoutBody(final Invocation.Builder requestBuilder) {
        return requestBuilder.build(httpMethod.toString());
    }
}
