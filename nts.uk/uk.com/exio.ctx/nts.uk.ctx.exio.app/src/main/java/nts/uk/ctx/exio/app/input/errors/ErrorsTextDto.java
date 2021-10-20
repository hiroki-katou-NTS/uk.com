package nts.uk.ctx.exio.app.input.errors;

import lombok.Value;

@Value
public class ErrorsTextDto {
	
	boolean execution;
	int pageNo;
	int errorsCount;
	String text;
}
