package nts.uk.ctx.at.shared.app.find.calculation.holiday.time;

import lombok.Data;

@Data
public class OverdayCalcHolidayDto {
	/**変更前の休出枠NO*/
	private int holidayWorkFrameNo;
	
	/** 変更後の法定内休出NO*/
	private int calcOverDayEnd;
	
	/** 変更後の法定外休出NO */
	private int statutoryHd;
	
	/** 変更後の祝日休出NO */
	private int excessHd;
	

}
