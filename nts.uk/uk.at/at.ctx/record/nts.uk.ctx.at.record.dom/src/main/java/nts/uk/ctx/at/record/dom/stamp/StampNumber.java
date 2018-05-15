package nts.uk.ctx.at.record.dom.stamp;

import nts.arc.primitive.StringPrimitiveValue;
//<<<<<<< HEAD:nts.uk/uk.at/at.ctx/record/nts.uk.ctx.at.record.dom/src/main/java/nts/uk/ctx/at/record/dom/stamp/StampNumber.java
//
//=======
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(20)
@StringCharType(CharType.ANY_HALF_WIDTH)
//>>>>>>> pj/at/dev/Team_B/zansu4:nts.uk/uk.at/at.ctx/record/nts.uk.ctx.at.record.dom/src/main/java/nts/uk/ctx/at/record/dom/stamp/card/stampcard/StampNumber.java
public class StampNumber extends StringPrimitiveValue<StampNumber> {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public StampNumber(String rawValue) {
		super(rawValue);
	}

}
