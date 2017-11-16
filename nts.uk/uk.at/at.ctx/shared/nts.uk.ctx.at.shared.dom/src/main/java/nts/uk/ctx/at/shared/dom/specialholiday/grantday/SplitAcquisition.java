package nts.uk.ctx.at.shared.dom.specialholiday.grantday;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum SplitAcquisition {
	/* 固定日数を付与する */
	FixedDay(0),
	/* 勤続年数を参照して付与する */
	ReferYearService(1);

	public final int value;
}
