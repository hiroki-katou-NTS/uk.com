package nts.uk.ctx.at.record.dom.workinformation.primitivevalue;

import nts.arc.primitive.PrimitiveValue;
import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 
 * @author nampt
 * 勤務種類コード
 *
 */
@StringCharType(CharType.ALPHA_NUMERIC)
@StringMaxLength(3)
public class WorkTypeCode extends StringPrimitiveValue<PrimitiveValue<String>>{
	
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	public WorkTypeCode(String rawValue) {
		super(rawValue);
	}

}
