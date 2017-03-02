package nts.uk.ctx.basic.dom.organization.position;

import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;



@StringCharType(CharType.ALPHABET)
@StringMaxLength(36)
public class HistoryId extends CodePrimitiveValue<HistoryId>{
	
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	public HistoryId(String rawValue) {
		super(rawValue);
	}

}