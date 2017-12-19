package nts.uk.ctx.at.shared.dom.calculation.holiday.time;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class OverdayHolidayAtten {
	/** 会社ID */
	private String companyId;

	/** 変更前の休出枠NO */
	private int holidayWorkFrameNo;

	/** 変更後の残業枠NO */
	private int overWorkNo;

	public static OverdayHolidayAtten createFromJavaType(String companyId, int holidayWorkFrameNo, int overWorkNo) {
		return new OverdayHolidayAtten(companyId, holidayWorkFrameNo, overWorkNo);
	}
}
