package nts.uk.ctx.at.schedule.dom.schedule.setting.modify.control;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;
/**
 * The class CorrectDeadline
 * @author phongtq
 *
 */
//予定修正期限
@IntegerRange(min = 0, max = 31)
public class CorrectDeadline extends IntegerPrimitiveValue<CorrectDeadline>{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * Instantiates a new scheduled break cnt.
	 *
	 * @param rawValue the raw value
	 */
	public CorrectDeadline(Integer rawValue) {
		super(rawValue);
	}
}
