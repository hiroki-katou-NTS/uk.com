/**
 * 10:29:18 AM Jun 6, 2017
 */
package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.primitives;

import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;

/**
 * @author hungnm
 *
 */
@StringMaxLength(3)
public class WorkingTimesheetCode extends CodePrimitiveValue<WorkingTimesheetCode> {
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new primitive code
	 *
	 * @param rawValue
	 *            the raw value
	 */
	public WorkingTimesheetCode(String rawValue) {
		super(rawValue);
	}
}
