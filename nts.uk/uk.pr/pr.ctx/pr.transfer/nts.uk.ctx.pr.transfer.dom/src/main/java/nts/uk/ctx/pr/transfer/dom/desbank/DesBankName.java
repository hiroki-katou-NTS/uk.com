package nts.uk.ctx.pr.transfer.dom.desbank;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 
 * @author HungTT - 振込元銀行名
 *
 */

@StringMaxLength(20)
public class DesBankName extends StringPrimitiveValue<DesBankName> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public DesBankName(String rawValue) {
		super(rawValue);
	}

}
