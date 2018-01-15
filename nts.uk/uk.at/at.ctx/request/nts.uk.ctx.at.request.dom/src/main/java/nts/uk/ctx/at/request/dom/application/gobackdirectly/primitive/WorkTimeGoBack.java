package nts.uk.ctx.at.request.dom.application.gobackdirectly.primitive;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerMaxValue;
@IntegerMaxValue(1440)
public class WorkTimeGoBack extends IntegerPrimitiveValue<WorkTimeGoBack> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public WorkTimeGoBack(Integer rawValue) {
		super(rawValue);
	}
}
