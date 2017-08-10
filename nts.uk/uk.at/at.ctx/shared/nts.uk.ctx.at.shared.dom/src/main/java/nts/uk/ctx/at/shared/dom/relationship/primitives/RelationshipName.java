package nts.uk.ctx.at.shared.dom.relationship.primitives;

import nts.arc.primitive.PrimitiveValue;
import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;
@StringMaxLength(20)
public class RelationshipName extends StringPrimitiveValue<PrimitiveValue<String>>{
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	public RelationshipName(String rawValue) {
		super(rawValue);
	}
}
