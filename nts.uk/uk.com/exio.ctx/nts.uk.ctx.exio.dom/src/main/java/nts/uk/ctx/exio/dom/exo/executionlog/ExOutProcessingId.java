package nts.uk.ctx.exio.dom.exo.executionlog;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;



@StringMaxLength(36)
public class ExOutProcessingId extends StringPrimitiveValue<ExOutProcessingId> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ExOutProcessingId(String rawValue) {
		super(rawValue);
	}

}
