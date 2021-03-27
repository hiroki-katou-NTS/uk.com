package nts.uk.ctx.at.shared.dom.scherec.application.appabsence;

import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;

/**
 *	続柄コード
 */
@StringMaxLength(2)
@StringCharType(CharType.ALPHA_NUMERIC)
public class RelationshipCDPrimitiveShare extends CodePrimitiveValue<RelationshipCDPrimitiveShare>{
	
	private static final long serialVersionUID = 1L;
	
	public RelationshipCDPrimitiveShare(String rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}

	

}
