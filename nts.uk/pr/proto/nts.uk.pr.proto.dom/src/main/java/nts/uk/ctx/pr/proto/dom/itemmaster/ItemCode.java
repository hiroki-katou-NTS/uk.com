package nts.uk.ctx.pr.proto.dom.itemmaster;

import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;

@StringCharType(CharType.NUMERIC)
@StringMaxLength(4)
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
