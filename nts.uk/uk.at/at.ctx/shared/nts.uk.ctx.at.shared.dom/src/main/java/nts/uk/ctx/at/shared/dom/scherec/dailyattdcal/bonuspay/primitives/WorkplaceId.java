/**
 * 10:25:37 AM Jun 6, 2017
 */
package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.primitives;

import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;

/**
 * @author hungnm
 *
 */
// 勤怠項目ID
@StringCharType(CharType.ALPHA_NUMERIC)
@StringMaxLength(36)
public class WorkplaceId extends CodePrimitiveValue<WorkplaceId> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new workplace id
	 *
	 * @param rawValue
	 *            the raw value
	 */
	public WorkplaceId(String rawValue) {
		super(rawValue);
	}
}
