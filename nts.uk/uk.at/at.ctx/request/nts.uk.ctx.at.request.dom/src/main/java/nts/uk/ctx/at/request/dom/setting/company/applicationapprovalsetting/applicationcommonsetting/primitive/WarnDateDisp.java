package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationcommonsetting.primitive;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

@IntegerRange(min = 1, max = 7)
public class WarnDateDisp extends IntegerPrimitiveValue<WarnDateDisp>{
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	public WarnDateDisp(int rawValue) {
		super(rawValue);
	}
}
