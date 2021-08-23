package nts.uk.ctx.exio.dom.input.errors;

import lombok.Value;

@Value
public class ErrorMessage {

	private final String text;
	
	public static ErrorMessage of(Exception ex) {
		return new ErrorMessage(ex.getMessage());
	}
}
