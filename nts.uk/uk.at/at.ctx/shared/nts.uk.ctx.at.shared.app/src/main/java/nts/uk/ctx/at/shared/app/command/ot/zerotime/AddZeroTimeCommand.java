package nts.uk.ctx.at.shared.app.command.ot.zerotime;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.zerotime.HdFromHd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.zerotime.HdFromWeekday;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.zerotime.WeekdayHoliday;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.zerotime.ZeroTime;

/**
 * @author phongtq 
 * The class Add Overday Calc Command
 */
@Data
@AllArgsConstructor
public class AddZeroTimeCommand {
	/** 0時跨ぎ計算を行なう */
	private int calcFromZeroTime;

	/** 法定内休日 */
	private int legalHd;

	/** 法定外休日 */
	private int nonLegalHd;

	/** 法定外祝日 */
	private int nonLegalPublicHd;

	/** 平日 */
	private int weekday1;

	/** 法定外休日 */
	private int nonLegalHd1;

	/** 法定外祝日 */
	private int nonLegalPublicHd1;

	/** 平日 */
	private int weekday2;

	/** 法定内休日 */
	private int legalHd2;

	/** 法定外祝日 */
	private int nonLegalHd2;

	/** 平日 */
	private int weekday3;

	/** 法定内休日 */
	private int legalHd3;

	/** 法定外休日 */
	private int nonLegalPublicHd3;

	/** 平日から休日の0時跨ぎ設定 */
	private List<WeekdayHolidayCommand> weekdayHoliday;

	/** 休日から平日への0時跨ぎ設定 */
	private List<HdFromWeekdayCommand> overdayHolidayAtten;

	/** 休日から休日への0時跨ぎ設定 */
	private List<HdFromHdHolidayCommand> overdayCalcHoliday;

	/**
	 * Convert to Domain Overday Calc
	 * 
	 * @param companyId
	 * @return
	 */
	public ZeroTime toDomain(String companyId) {
		return ZeroTime.builder().companyId(companyId).calcFromZeroTime(calcFromZeroTime).legalHd(legalHd)
				.nonLegalHd(nonLegalHd).nonLegalPublicHd(nonLegalPublicHd).weekday1(weekday1).nonLegalHd1(nonLegalHd1)
				.nonLegalPublicHd1(nonLegalPublicHd1).weekday2(weekday2).legalHd2(legalHd2).nonLegalHd2(nonLegalHd2)
				.weekday3(weekday3).legalHd3(legalHd3).nonLegalPublicHd3(nonLegalPublicHd3)
				.weekdayHoliday(this.toDomainWeekdayHoliday(companyId))
				.overdayHolidayAtten(this.toDomainOverdayHolidayAtten(companyId))
				.overdayCalcHoliday(this.toDomainOverdayCalcHoliday(companyId)).build();
	}

	/**
	 * Convert to Domain Weekday Holiday
	 * 
	 * @param companyId
	 * @return
	 */
	private List<WeekdayHoliday> toDomainWeekdayHoliday(String companyId) {
		if (weekdayHoliday == null) {
			return null;
		}
		;
		return this.weekdayHoliday.stream().map(x -> WeekdayHoliday.createFromJavaType(companyId,
				x.getOverworkFrameNo(), x.getWeekdayNo(), x.getExcessHolidayNo(), x.getExcessSphdNo()))
				.collect(Collectors.toList());
	}

	/**
	 * Convert to Domain Overday Holiday Atten
	 * 
	 * @param companyId
	 * @return
	 */
	private List<HdFromWeekday> toDomainOverdayHolidayAtten(String companyId) {
		if (overdayHolidayAtten == null) {
			return null;
		}
		;
		return this.overdayHolidayAtten.stream()
				.map(x -> HdFromWeekday.createFromJavaType(companyId, x.getHolidayWorkFrameNo(), x.getOverWorkNo()))
				.collect(Collectors.toList());
	}

	private List<HdFromHd> toDomainOverdayCalcHoliday(String companyId) {
		if (overdayCalcHoliday == null) {
			return null;
		}
		;
		return this.overdayCalcHoliday.stream().map(x -> HdFromHd.createFromJavaType(companyId,
				x.getHolidayWorkFrameNo(), x.getCalcOverDayEnd(), x.getStatutoryHd(), x.getExcessHd()))
				.collect(Collectors.toList());
	}
}
