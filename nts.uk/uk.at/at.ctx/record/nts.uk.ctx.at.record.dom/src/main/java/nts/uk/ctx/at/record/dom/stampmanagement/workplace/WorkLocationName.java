package nts.uk.ctx.at.record.dom.stampmanagement.workplace;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(20)
/**
 * 
 * @author hieult
 *
 */
public class WorkLocationName extends StringPrimitiveValue<WorkLocationName> {

	public WorkLocationName(String rawValue) {
		super(rawValue);
	}
	private static final long serialVersionUID = 1L;

}
