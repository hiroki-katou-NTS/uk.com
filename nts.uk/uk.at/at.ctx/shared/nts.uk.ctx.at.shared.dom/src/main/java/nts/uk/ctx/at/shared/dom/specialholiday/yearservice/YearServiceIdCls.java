package nts.uk.ctx.at.shared.dom.specialholiday.yearservice;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum YearServiceIdCls {

	/* 入社日を勤続年数基準日 */
	EmploymentDateStandardYears(0),

	/* 特休基準日を勤続年数基準日 */
	SpecialGrantStandardDate(1);

	public final int value;
}
