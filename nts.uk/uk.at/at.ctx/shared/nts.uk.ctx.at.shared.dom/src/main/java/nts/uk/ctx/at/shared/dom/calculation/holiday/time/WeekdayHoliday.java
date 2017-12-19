package nts.uk.ctx.at.shared.dom.calculation.holiday.time;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class WeekdayHoliday {

	/** 会社ID */
	private String companyId;

	/** 変更前の残業枠NO */
	private int overworkFrameNo;

	/** 変更後の残業枠NO */
	private int weekdayNo;

	/** 変更後の法定外休出NO */
	private int excessHolidayNo;

	/** 変更後の祝日休出NO */
	private int excessSphdNo;

	public static WeekdayHoliday createFromJavaType(String companyId, int overworkFrameNo, int weekdayNo,
			int excessHolidayNo, int excessSphdNo) {
		return new WeekdayHoliday(companyId, overworkFrameNo, weekdayNo, excessHolidayNo, excessSphdNo);
	}
}
