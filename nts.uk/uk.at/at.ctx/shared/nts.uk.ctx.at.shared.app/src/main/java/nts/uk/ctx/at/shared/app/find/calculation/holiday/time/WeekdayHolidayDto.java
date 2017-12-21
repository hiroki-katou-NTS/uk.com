package nts.uk.ctx.at.shared.app.find.calculation.holiday.time;

import lombok.Data;

@Data
public class WeekdayHolidayDto {
	/** 変更前の残業枠NO */
	private int overworkFrameNo;

	/** 変更後の残業枠NO */
	private int weekdayNo;

	/** 変更後の法定外休出NO */
	private int excessHolidayNo;

	/** 変更後の祝日休出NO */
	private int excessSphdNo;
	
	
}
