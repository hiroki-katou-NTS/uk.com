package nts.uk.ctx.sys.portal.dom.toppagepart.primitive;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * @author LamDT
 */
@StringMaxLength(30)
public class ToppagePartName extends StringPrimitiveValue<ToppagePartName> {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	public ToppagePartName(String rawValue) {
		super(rawValue);
	}

}