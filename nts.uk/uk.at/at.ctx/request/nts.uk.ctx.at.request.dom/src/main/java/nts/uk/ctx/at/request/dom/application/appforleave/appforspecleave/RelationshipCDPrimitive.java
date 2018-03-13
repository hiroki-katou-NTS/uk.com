package nts.uk.ctx.at.request.dom.application.appforleave.appforspecleave;

import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;

/**
 * @author loivt
 *	続柄コード
 */
@StringMaxLength(2)
@StringCharType(CharType.ALPHA_NUMERIC)
public class RelationshipCDPrimitive extends CodePrimitiveValue<RelationshipCDPrimitive>{
	
	private static final long serialVersionUID = 1L;
	
	public RelationshipCDPrimitive(String rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}

	

}
