package nts.arc.layer.ws.json;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.BeanDeserializerFactory;
import com.fasterxml.jackson.databind.deser.DefaultDeserializationContext;
import com.fasterxml.jackson.databind.deser.DeserializerFactory;
import com.fasterxml.jackson.databind.ser.BeanSerializerFactory;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;
import com.fasterxml.jackson.databind.ser.SerializerFactory;

/**
 * The Class JacksonConfig.
 * Customize jackson config.
 */
@Provider
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class JacksonConfig implements ContextResolver<ObjectMapper> {

    /** The mapper. */
    private ObjectMapper mapper;

    /**
     * Instantiates a new jackson config.
     */
    public JacksonConfig() {
        // Init default object.
        ObjectMapper om = new ObjectMapper();
        
        // Init sf/df for hc.
        SerializerFactory sf = BeanSerializerFactory.instance/*.withAdditionalSerializers(new HcJsonSerializers())*/;
        DeserializerFactory df = BeanDeserializerFactory.instance/*.withAdditionalDeserializers(new HcJsonDeserializers())*/; 
        
        this.mapper = new ObjectMapper(null,
                new DefaultSerializerProvider.Impl().createInstance(om.getSerializationConfig(), sf),
                new DefaultDeserializationContext.Impl(df));
        this.mapper.setSerializerFactory(sf);
        
        /* Webserviceに対して、JavaScriptから余分なデータ（パラメータクラスに無いもの）を送った場合に、
         * UnrecognizedPropertyExceptionが起きないようにする。
         * ko.toJSで「画面上でしか必要のないもの」もまとめてマッピングするため。
         */
        this.mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.ws.rs.ext.ContextResolver#getContext(java.lang.Class)
     */
    @Override
    public ObjectMapper getContext(Class<?> type) {
        return this.mapper;
    }
}