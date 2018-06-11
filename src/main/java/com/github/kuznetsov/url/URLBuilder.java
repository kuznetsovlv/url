package com.github.kuznetsov.url;

import com.github.kuznetsov.tcp.TCPIncorrectPortException;
import com.github.kuznetsov.tcp.TCPPort;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author leonid
 */
public class URLBuilder {
    private String protocol;
    private String host;
    private TCPPort port;
    private String path;
    private Map<String, Object> parameters;
    private String hash;
    
    public URLBuilder setProtool(String protocol) {
        this.protocol = protocol.trim();
        return this;
    }
    
    public URLBuilder setHost(String host) {
        this.host = host.trim();
        return this;
    }
    
    public URLBuilder setPort(int port) throws TCPIncorrectPortException {
        this.port = new TCPPort(port);
        return this;
    }
    
    public URLBuilder setPort(TCPPort port) {
        this.port = port;
        return this;
    }
    
    public URLBuilder setHash(String hash) {
        this.hash = hash.trim();
        return this;
    }
    
    public URLBuilder addParameter(String key, Object value) {
        if (parameters == null) {
            parameters = new HashMap<>();
        }
        
        parameters.put(key.trim(), value);
        
        return this;
    }
    
    public String buildStringURL() {
        StringBuilder url = new StringBuilder("");
        
        if(protocol != null && !protocol.isEmpty()) {
            url.append(protocol).append("://");
        }
        
        if(host != null && !host.isEmpty()) {
            url.append(host);
        }
        
        if(port != null) {
            url.append(":").append(port.toString());
        }
        
        if (path != null && !path.isEmpty()) {
            path = path.replaceAll("\\", "/").trim();
            
            if (!path.substring(0, 1).equals("/")) {
                url.append("/");
            }
            
            url.append(path);
        }
        
        if (parameters != null && !parameters.isEmpty()) {
            url.append("?");
            
            Set<Map.Entry<String, Object>> entries = parameters.entrySet();
            boolean first = true;
            
            for(Map.Entry<String, Object> entry: entries) {
                if (!first) {
                    url.append("&");
                }
                url.append(entry.getKey()).append("=").append(entry.getValue().toString());
            }
        }
        
        if(hash != null && !hash.isEmpty()) {
            url.append("#").append(hash);
        }
        
        return url.toString();
    }
    
    public URL build() throws MalformedURLException {
        return new URL(buildStringURL());
    }
}
