package nts.uk.ctx.pr.transfer.dom.sourcebank;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 
 * @author HungTT - 振込元銀行名
 *
 */

@StringMaxLength(20)
public class SourceBankName extends StringPrimitiveValue<SourceBankName> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public SourceBankName(String rawValue) {
		super(rawValue);
	}

}
