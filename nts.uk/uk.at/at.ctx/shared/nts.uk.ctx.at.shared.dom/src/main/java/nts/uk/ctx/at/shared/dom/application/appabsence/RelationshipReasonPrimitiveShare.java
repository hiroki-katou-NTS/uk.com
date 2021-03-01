package nts.uk.ctx.at.shared.dom.application.appabsence;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * @author loivt
 *	続柄理由
 */
@StringMaxLength(20)
public class RelationshipReasonPrimitiveShare extends StringPrimitiveValue<RelationshipReasonPrimitiveShare>{
	
	private static final long serialVersionUID = 1L;

	public RelationshipReasonPrimitiveShare(String rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}

}
