package nts.uk.ctx.pr.transfer.dom.sourcebank;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 
 * @author HungTT - 委託者用途
 *
 */

@StringMaxLength(20)
public class EntrustorUse extends StringPrimitiveValue<EntrustorUse> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public EntrustorUse(String rawValue) {
		super(rawValue);
	}

}
