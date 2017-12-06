package nts.uk.ctx.bs.employee.dom.jobtitle.jobtitlehistory;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(100)
public class AffJobHistoryItemNote extends StringPrimitiveValue<AffJobHistoryItemNote>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AffJobHistoryItemNote(String rawValue) {
		super(rawValue);
	}

}
