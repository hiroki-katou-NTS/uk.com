package nts.uk.ctx.at.shared.app.command.calculation.holiday.time;
/**
 * @author phongtq
 * The class Add Overday Calc Command
 */
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.shared.dom.calculation.holiday.time.OverdayCalc;
import nts.uk.ctx.at.shared.dom.calculation.holiday.time.OverdayCalcHoliday;
import nts.uk.ctx.at.shared.dom.calculation.holiday.time.OverdayHolidayAtten;
import nts.uk.ctx.at.shared.dom.calculation.holiday.time.WeekdayHoliday;

@Data
@AllArgsConstructor
public class AddOverdayCalcCommand {
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
	
	/**平日から休日の0時跨ぎ設定*/
	private List<WeekdayHolidayCommand> weekdayHoliday;
	
	/**休日から平日への0時跨ぎ設定*/
	private List<OverdayHolidayAttenCommand> overdayHolidayAtten;
	
	/**休日から休日への0時跨ぎ設定*/
	private List<OverdayCalcHolidayCommand> overdayCalcHoliday;
	
	/**
	 * Convert to Domain Overday Calc
	 * @param companyId
	 * @return
	 */
	public OverdayCalc toDomain(String companyId){
		return OverdayCalc.builder()
				.companyId(companyId)
				.calcOverDayEnd(calcOverDayEnd)
				.statutoryHd(statutoryHd)
				.excessHd(excessHd)
				.excessSpecialHoliday(excessSpecialHoliday)
				.weekDayStatutoryHd(weekDayStatutoryHd)
				.excessStatutoryHd(excessStatutoryHd)
				.excessStatutorSphd(excessStatutorSphd)
				.weekDayLegalHd(weekDayLegalHd)
				.excessLegalHd(excessLegalHd)
				.excessLegalSphd(excessLegalSphd)
				.weekDayPublicHd(weekDayPublicHd)
				.excessPublicHd(excessPublicHd)
				.excessPublicSphd(excessPublicSphd)
				.weekdayHoliday(this.toDomainWeekdayHoliday(companyId))
				.overdayHolidayAtten(this.toDomainOverdayHolidayAtten(companyId))
				.overdayCalcHoliday(this.toDomainOverdayCalcHoliday(companyId))
				.build();
	}
	
	/**
	 * Convert to Domain Weekday Holiday
	 * @param companyId
	 * @return
	 */
	private List<WeekdayHoliday> toDomainWeekdayHoliday(String companyId) {
		return this.weekdayHoliday.stream().map(x -> WeekdayHoliday.createFromJavaType(
				companyId,
				x.getOverworkFrameNo(), 
				x.getWeekdayNo(), 
				x.getExcessHolidayNo(), 
				x.getExcessSphdNo())).collect(Collectors.toList());
	}
	
	/**
	 * Convert to Domain Overday Holiday Atten
	 * @param companyId
	 * @return
	 */
	private List<OverdayHolidayAtten> toDomainOverdayHolidayAtten(String companyId) {
		return this.overdayHolidayAtten.stream().map(x -> OverdayHolidayAtten.createFromJavaType(
				companyId, 
				x.getHolidayWorkFrameNo(), 
				x.getOverWorkNo())).collect(Collectors.toList());
	}
	
	private List<OverdayCalcHoliday> toDomainOverdayCalcHoliday(String companyId) {
		return this.overdayCalcHoliday.stream().map(x -> OverdayCalcHoliday.createFromJavaType(
				companyId, 
				x.getHolidayWorkFrameNo(), 
				x.getCalcOverDayEnd(), 
				x.getStatutoryHd(), 
				x.getExcessHd())).collect(Collectors.toList());
	}
}
