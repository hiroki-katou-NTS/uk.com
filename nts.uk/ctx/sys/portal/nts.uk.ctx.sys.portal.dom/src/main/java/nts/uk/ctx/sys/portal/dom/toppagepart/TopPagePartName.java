package nts.uk.ctx.sys.portal.dom.toppagepart;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * @author LamDT
 */
@StringMaxLength(30)
public class TopPagePartName extends StringPrimitiveValue<TopPagePartName> {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	public TopPagePartName(String rawValue) {
		super(rawValue);
	}

}