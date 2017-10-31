package nts.uk.ctx.at.shared.dom.specialholiday.grantrelationship.primitives;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.PrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;
/**
 * 
 * @author yennth
 *
 */
@IntegerRange(max=366, min=0)
public class GrantRelationshipDay extends IntegerPrimitiveValue<PrimitiveValue<Integer>>{
	private static final long serialVersionUID = 1L;
	public GrantRelationshipDay(Integer rawValue) {
		super(rawValue);
	}

}
