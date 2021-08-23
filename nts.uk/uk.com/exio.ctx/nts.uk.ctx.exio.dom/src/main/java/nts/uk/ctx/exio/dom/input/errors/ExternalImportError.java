package nts.uk.ctx.exio.dom.input.errors;

import lombok.Value;

@Value
public class ExternalImportError {

	int csvRowNo;
	Integer itemNo;
	String message;
	
	public static ExternalImportError record(int csvRowNo, String message) {
		return new ExternalImportError(csvRowNo, null, message);
	}
	
	public static ExternalImportError of(int csvRowNo, ItemError error) {
		return new ExternalImportError(csvRowNo, error.getItemNo(), error.getMessage());
	}
	
	public static ExternalImportError of(RecordError error) {
		return new ExternalImportError(error.getCsvRowNo(), null, error.getMessage());
	}
	
}
