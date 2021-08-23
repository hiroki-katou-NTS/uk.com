package nts.uk.ctx.exio.dom.input.errors;

import static java.util.stream.Collectors.*;

import java.util.Comparator;
import java.util.List;

import lombok.val;
import nts.uk.ctx.exio.dom.input.ExecutionContext;

public class ExternalImportErrors {

	private final List<ExternalImportError> errors;
	
	public ExternalImportErrors(List<ExternalImportError> errors) {
		
		this.errors = errors.stream()
				.sorted(Comparator.comparing(e -> e.getCsvRowNo()))
				.collect(toList());
	}
	
	public int count() {
		return errors.size();
	}
	
	public String toText(RequireToText require, ExecutionContext context) {
		
		val sb = new StringBuilder();
		
		errors.forEach(e -> e.toText(require, context, sb));
		
		return sb.toString();
	}
	
	public static interface RequireToText extends ExternalImportError.RequireToText {
	}
}
