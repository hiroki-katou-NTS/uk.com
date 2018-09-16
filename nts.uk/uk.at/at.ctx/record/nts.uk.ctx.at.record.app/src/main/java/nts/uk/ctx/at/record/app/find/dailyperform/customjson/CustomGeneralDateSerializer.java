package nts.uk.ctx.at.record.app.find.dailyperform.customjson;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import nts.arc.time.GeneralDate;

public class CustomGeneralDateSerializer extends JsonDeserializer<GeneralDate> {

	public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd");

	@Override
	public GeneralDate deserialize(JsonParser jsonparser, DeserializationContext arg1)
			throws IOException, JsonProcessingException {
		String dateAsString = jsonparser.getText();
		if (dateAsString.equals("") || dateAsString == null)
			return null;
		LocalDate localDate = LocalDate.parse(dateAsString, FORMATTER);
		return GeneralDate.localDate(localDate);
	}

}
