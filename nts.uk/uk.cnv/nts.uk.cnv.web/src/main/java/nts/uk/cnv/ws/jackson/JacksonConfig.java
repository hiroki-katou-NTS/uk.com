package nts.uk.cnv.ws.jackson;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import lombok.val;
import nts.arc.layer.ws.json.serializer.GeneralDateDeserializer;
import nts.arc.layer.ws.json.serializer.GeneralDateSerializer;
import nts.arc.layer.ws.json.serializer.GeneralDateTimeDeserializer;
import nts.arc.layer.ws.json.serializer.GeneralDateTimeSerializer;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

@Provider
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class JacksonConfig implements ContextResolver<ObjectMapper> {

    private final ObjectMapper mapper;
    
    public JacksonConfig() {
    	
    	mapper = new ObjectMapper();
    	
    	val module = new SimpleModule();
        module.addSerializer(GeneralDate.class, new GeneralDateSerializer());
        module.addDeserializer(GeneralDate.class, new GeneralDateDeserializer());
        module.addSerializer(GeneralDateTime.class, new GeneralDateTimeSerializer());
        module.addDeserializer(GeneralDateTime.class, new GeneralDateTimeDeserializer());
    	
    	mapper.registerModule(module);
    	
    	/* Webserviceに対して、JavaScriptから余分なデータ（パラメータクラスに無いもの）を送った場合に、
         * UnrecognizedPropertyExceptionが起きないようにする。
         */
        this.mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
	
	@Override
	public ObjectMapper getContext(Class<?> type) {
		return mapper;
	}

}
