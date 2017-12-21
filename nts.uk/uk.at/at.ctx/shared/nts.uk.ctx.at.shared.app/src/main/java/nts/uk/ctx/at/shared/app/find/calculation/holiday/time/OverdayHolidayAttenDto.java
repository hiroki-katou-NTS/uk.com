package nts.uk.ctx.at.shared.app.find.calculation.holiday.time;

import lombok.Data;

@Data
public class OverdayHolidayAttenDto {
	/** 変更前の休出枠NO */
	private int holidayWorkFrameNo;

	/** 変更後の残業枠NO */
	private int overWorkNo;
	
}
