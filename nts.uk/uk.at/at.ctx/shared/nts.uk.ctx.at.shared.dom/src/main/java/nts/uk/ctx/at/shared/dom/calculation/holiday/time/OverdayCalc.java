package nts.uk.ctx.at.shared.dom.calculation.holiday.time;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

@AllArgsConstructor
@Getter
@Builder
public class OverdayCalc extends AggregateRoot {

	/** 会社ID */
	private String companyId;

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
	
	private List<WeekdayHoliday> weekdayHoliday;
	
	private List<OverdayHolidayAtten> overdayHolidayAtten;
	
	private List<OverdayCalcHoliday> overdayCalcHoliday;

	public static OverdayCalc createFromJavaType(String companyId, int calcOverDayEnd, int statutoryHd, int excessHd,
			int excessSpecialHoliday, int weekDayStatutoryHd, int excessStatutoryHd, int excessStatutorSphd,
			int weekDayLegalHd, int excessLegalHd, int excessLegalSphd, int weekDayPublicHd, int excessPublicHd,
			int excessPublicSphd,List<WeekdayHoliday>  weekdayHoliday, List<OverdayHolidayAtten> overdayHolidayAtten, List<OverdayCalcHoliday> overdayCalcHoliday) {
		return new OverdayCalc(companyId, calcOverDayEnd, statutoryHd, excessHd, excessSpecialHoliday,
				weekDayStatutoryHd, excessStatutoryHd, excessStatutorSphd, weekDayLegalHd, excessLegalHd,
				excessLegalSphd, weekDayPublicHd, excessPublicHd, excessPublicSphd, weekdayHoliday, overdayHolidayAtten, overdayCalcHoliday);
	}
}
