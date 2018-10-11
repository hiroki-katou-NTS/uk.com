package nts.uk.ctx.pr.transfer.dom.desbank;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 
 * @author HungTT - 振込依頼者名
 *
 */

@StringMaxLength(40)
public class TransferRequesterName extends StringPrimitiveValue<TransferRequesterName> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TransferRequesterName(String rawValue) {
		super(rawValue);
	}

}
