package nts.uk.screen.at.app.query.kdp.kdps01.c;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class DisplayConfirmStampResultScreenCQuery {

	/**
	 * 打刻日時
	 */

	private String startDate;

	/**
	 * 打刻日時
	 */
	private String endDate;
	/**
	 * 反映対象日
	 */
	private String baseDate;
	/**
	 * 表示項目一覧(勤怠項目ID(リスト))
	 */
	private List<Integer> attendanceItemIds;

	public GeneralDate getBaseDate() {
		return GeneralDate.fromString(this.baseDate, "yyyy/MM/dd");
	}

	public DatePeriod getPeriod() {
		return new DatePeriod(GeneralDate.fromString(this.startDate, "yyyy/MM/dd"),
				GeneralDate.fromString(this.endDate, "yyyy/MM/dd"));
	}
}
