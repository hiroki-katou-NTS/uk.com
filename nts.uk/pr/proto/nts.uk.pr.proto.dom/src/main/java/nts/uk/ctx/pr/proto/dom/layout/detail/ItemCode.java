package nts.uk.ctx.pr.proto.dom.layout.detail;

import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLengh;
import nts.uk.shr.com.primitive.CodePrimitiveValue;

@StringCharType(CharType.NUMERIC)
@StringMaxLengh(4)
public class ItemCode extends CodePrimitiveValue<ItemCode>{
	/**
	 * Constructor
	 * @param rawValue
	 */
	public ItemCode(String rawValue) {
		super(rawValue);		
	}

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

}
