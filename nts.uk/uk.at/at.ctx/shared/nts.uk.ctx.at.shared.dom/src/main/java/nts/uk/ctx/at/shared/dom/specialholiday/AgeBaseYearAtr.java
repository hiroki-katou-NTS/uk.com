package nts.uk.ctx.at.shared.dom.specialholiday;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum AgeBaseYearAtr {
	/* 翌月 */
	NextMonth(0),
	/* 当月 */
	ThisMonth(1);

	public final int value;
}
