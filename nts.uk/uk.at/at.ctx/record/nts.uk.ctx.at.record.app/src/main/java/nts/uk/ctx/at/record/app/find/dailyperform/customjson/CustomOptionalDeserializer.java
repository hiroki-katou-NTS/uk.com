package nts.uk.ctx.at.record.app.find.dailyperform.customjson;

import java.io.IOException;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CustomOptionalDeserializer extends JsonDeserializer<Optional<Object>> implements ContextualDeserializer {

	private JavaType javaType;

	@Override
	public Optional<Object> deserialize(JsonParser jp, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		Object reference = ctxt.findRootValueDeserializer(javaType).deserialize(jp, ctxt);
		return Optional.of(reference);
	}

	@Override
	public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property)
			throws JsonMappingException {
		JavaType collectionType = property.getType();
		javaType = collectionType.containedType(0);
		return this;
	}

	public CustomOptionalDeserializer(JavaType javaType) {
		super();
		this.javaType = javaType;
	}

	@Override
	public Optional<Object> getNullValue() {
		return Optional.empty();
	}
}
