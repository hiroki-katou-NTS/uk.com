package nts.uk.ctx.exio.dom.exo.outitem;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * @author son.tc
 *
 */
@StringMaxLength(3)
public class CondSetCd extends StringPrimitiveValue<CondSetCd> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CondSetCd(String rawValue) {
		super(rawValue);
	}
}
