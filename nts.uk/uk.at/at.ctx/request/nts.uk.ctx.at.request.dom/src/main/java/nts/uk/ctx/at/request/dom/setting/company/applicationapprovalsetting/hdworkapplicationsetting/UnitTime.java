package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.hdworkapplicationsetting;

import lombok.AllArgsConstructor;
import nts.arc.primitive.constraint.IntegerRange;

@AllArgsConstructor
@IntegerRange(max = 6, min =0)
public enum UnitTime {
	/**
	 * 1分
	 */
	ONEMIN(0),
	/**
	 * 5分
	 */
	FIVEMIN(1),
	/**
	 * 10分
	 */
	TENMIN(2),
	/**
	 * 15分
	 */
	FIFTEENMIN(3),
	/**
	 * 20分
	 */
	TWENTYMIN(4),
	/**
	 * 25分
	 */
	TWENTY_FIVE(5),
	/**
	 * 30分
	 */
	THIRTY(6);
	public final int value; 
}
