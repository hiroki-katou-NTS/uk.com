package nts.uk.ctx.exio.dom.exo.outcnddetail;

import nts.arc.primitive.PrimitiveValue;
import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.ZeroPaddedCode;

@StringMaxLength(3)
@StringCharType(CharType.NUMERIC)
@ZeroPaddedCode
public class ConditionSettingCd  extends StringPrimitiveValue<PrimitiveValue<String>>  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ConditionSettingCd(String rawValue) {
		super(rawValue);
	}
}
