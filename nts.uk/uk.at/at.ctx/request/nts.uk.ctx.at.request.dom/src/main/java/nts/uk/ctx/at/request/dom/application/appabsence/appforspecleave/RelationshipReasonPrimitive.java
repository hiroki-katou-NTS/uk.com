package nts.uk.ctx.at.request.dom.application.appabsence.appforspecleave;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * @author loivt
 *	続柄理由
 */
@StringMaxLength(20)
public class RelationshipReasonPrimitive extends StringPrimitiveValue<RelationshipReasonPrimitive>{
	
	private static final long serialVersionUID = 1L;

	public RelationshipReasonPrimitive(String rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}

}
