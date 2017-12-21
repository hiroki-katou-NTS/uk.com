package nts.uk.ctx.at.shared.app.find.calculation.holiday.time;

import java.util.List;

import lombok.Data;

@Data
public class OverdayCalcDto {
	/** 0時跨ぎ計算を行なう */
	private int calcOverDayEnd;

	/** 法定内休日 */
	private int statutoryHd;

	/** 法定外休日 */
	private int excessHd;

	/** 法定外祝日 */
	private int excessSpecialHoliday;

	/** 平日 */
	private int weekDayStatutoryHd;

	/** 法定外休日 */
	private int excessStatutoryHd;

	/** 法定外祝日 */
	private int excessStatutorSphd;

	/** 平日 */
	private int weekDayLegalHd;

	/** 法定外休日 */
	private int excessLegalHd;

	/** 法定外祝日 */
	private int excessLegalSphd;

	/** 平日 */
	private int weekDayPublicHd;

	/** 法定外休日 */
	private int excessPublicHd;

	/** 法定外休日 */
	private int excessPublicSphd;
	
	private List<WeekdayHolidayDto> weekdayHoliday;
	
	private List<OverdayHolidayAttenDto> overdayHolidayAtten;
	
	private List<OverdayCalcHolidayDto> overdayCalcHoliday;
	
	
}
