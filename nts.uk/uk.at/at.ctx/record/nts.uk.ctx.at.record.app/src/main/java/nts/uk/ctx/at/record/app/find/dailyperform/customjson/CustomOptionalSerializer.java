package nts.uk.ctx.at.record.app.find.dailyperform.customjson;

import java.io.IOException;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;


public class CustomOptionalSerializer  extends JsonSerializer<Optional<Object>>{

	@Override
	public void serialize(Optional<Object> value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
		if (value.isPresent()) {
			provider.defaultSerializeValue(value.get(), jgen);
		} else {
			provider.defaultSerializeNull(jgen);
		}
	}
}
