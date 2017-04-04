package nts.uk.ctx.basic.dom.organization.workplace;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;


@StringMaxLength(12)
public class WorkPlaceShortName extends StringPrimitiveValue<WorkPlaceShortName>{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	public WorkPlaceShortName(String rawValue) {
		super(rawValue);
	}

}
