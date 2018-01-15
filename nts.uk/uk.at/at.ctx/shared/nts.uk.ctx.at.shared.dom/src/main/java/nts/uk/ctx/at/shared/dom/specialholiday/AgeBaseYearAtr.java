package nts.uk.ctx.at.shared.dom.specialholiday;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum AgeBaseYearAtr {
	/**当年 */
	THIS_MONTH(0),
	/** 翌年 */
	NEXT_MONTH(1);
	

	public final int value;
}
