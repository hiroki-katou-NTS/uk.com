package nts.uk.ctx.exio.dom.exi.condset;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;
/**
 * 
 * @author HungTT
 *
 */
@StringMaxLength(40)
public class AcceptanceConditionName extends StringPrimitiveValue<AcceptanceConditionName> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AcceptanceConditionName(String rawValue) {
		super(rawValue);
	}

}
