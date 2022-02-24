package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.deductiontime;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import nts.uk.ctx.at.shared.dom.PremiumAtr;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanDuplication;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.HolidayCalcMethodSet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.ActualWorkTimeSheetAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.ActualWorkingTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.TimeSpanForDailyCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.MidNightTimeSheetForCalcList;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 控除時間分時刻をずらす時間帯
 * @author daiki_ichioka
 *
 */
public class StaggerDiductionTimeSheet extends ActualWorkingTimeSheet {

	public StaggerDiductionTimeSheet(TimeSpanForDailyCalc timeSheet, TimeRoundingSetting rounding, List<TimeSheetOfDeductionItem> deductionTimeSheets) {
		super(timeSheet, rounding, new ArrayList<>(), deductionTimeSheets, new ArrayList<>(), new ArrayList<>(), MidNightTimeSheetForCalcList.createEmpty());
	}
	
	/**
	 * 後ろにずらした終了時刻を取得する
	 * @param atr
	 * @param commonSet
	 * @param holidaySet
	 * @return 終了時刻
	 */
	public TimeWithDayAttr getForwardEnd(ActualWorkTimeSheetAtr atr, WorkTimezoneCommonSet commonSet, HolidayCalcMethodSet holidaySet) {
		List<TimeSheetOfDeductionItem> orderByAsc = this.deductionTimeSheet.stream()
				.sorted((x , y) -> x.start().compareTo(y.start()))
				.collect(Collectors.toList());
		List<TimeSheetOfDeductionItem> newList = new ArrayList<>();
		TimeWithDayAttr end = this.timeSheet.getEnd();
		for(TimeSheetOfDeductionItem item : orderByAsc) {
			TimeSpanDuplication duplication = item.getTimeSheet().checkDuplication(new TimeSpanForDailyCalc(this.timeSheet.getStart(), end));
			if(duplication.isDuplicated()) {
				newList.add(item);
				end = new StaggerDiductionTimeSheet(this.timeSheet, this.rounding, newList).forwardByAllDeductionTime(atr, commonSet, holidaySet);
			}
		}
		return end;
	}
	
	/**
	 * 全ての控除時間分後ろにずらした終了時刻を取得する
	 * @param atr
	 * @param commonSet
	 * @param holidaySet
	 * @return 終了時刻
	 */
	private TimeWithDayAttr forwardByAllDeductionTime(ActualWorkTimeSheetAtr atr, WorkTimezoneCommonSet commonSet, HolidayCalcMethodSet holidaySet) {
		this.grantRoundingToDeductionTimeSheet(atr, commonSet);
		if(atr.isWithinWorkTime()) {
			AttendanceTime within = this.calcDeductionTime(holidaySet, PremiumAtr.RegularWork, Optional.of(commonSet.getGoOutSet()));
			return this.timeSheet.getEnd().forwardByMinutes(within.valueAsMinutes());
		}
		AttendanceTime outside = this.calcDeductionTime(Optional.of(commonSet.getGoOutSet()));
		return this.timeSheet.getEnd().forwardByMinutes(outside.valueAsMinutes());
	}
}
