package nts.uk.ctx.at.shared.dom.remainingnumber.algorithm;

import lombok.AllArgsConstructor;

/**
 * 
 * @author sonnlb 休暇種類
 */
@AllArgsConstructor
public enum HolidayType {

	/** 年休 */
	ANNUAL(0),
	/** 代休 */
	SUBSTITUTE(1),
	/** 特休 */
	SPECIAL(2),
	/** 60H超休 */
	SIXTYHOUR(3),
	/** 子の看護 */
	CHILDCARE(4),
	/** 介護 */
	CARE(5);

	public final Integer value;
}
