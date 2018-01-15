package nts.uk.ctx.at.record.dom.worklocation;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
@StringMaxLength(4)
/**
 * 
 * @author hieult
 *
 */
@StringCharType(CharType.ALPHA_NUMERIC)
public class WorkLocationCD extends StringPrimitiveValue<WorkLocationCD>{

	public WorkLocationCD(String rawValue) {
		super(rawValue);
	}

	private static final long serialVersionUID = 1L;

}
