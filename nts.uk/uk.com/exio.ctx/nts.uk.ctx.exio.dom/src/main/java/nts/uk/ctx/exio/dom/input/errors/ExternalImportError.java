package nts.uk.ctx.exio.dom.input.errors;

import static java.util.stream.Collectors.*;

import java.util.List;

import lombok.Value;

@Value
public class ExternalImportError {

	int csvRowNo;
	String message;
	
	public static ExternalImportError of(int csvRowNo, ItemError error) {
		return new ExternalImportError(csvRowNo, error.getMessage());
	}
	
	public static ExternalImportError of(int csvRowNo, InterItemError error) {
		return new ExternalImportError(csvRowNo, error.getMessage());
	}
	
	public static ExternalImportError of(RecordError error) {
		return new ExternalImportError(error.getCsvRowNo(), error.getMessage());
	}
	
	public static List<ExternalImportError> of(InterRecordError error) {
		return error.getCsvRowNos().stream()
				.map(row -> new ExternalImportError(row, error.getMessage()))
				.collect(toList());
	}
}
