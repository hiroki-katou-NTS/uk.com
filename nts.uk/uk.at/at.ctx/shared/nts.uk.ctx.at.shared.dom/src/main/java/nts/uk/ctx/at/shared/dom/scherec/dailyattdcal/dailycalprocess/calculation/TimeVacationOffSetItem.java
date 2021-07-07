package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation;

import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.TimevacationUseTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.CalculationTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.deductiontime.DeductionAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.deductiontime.TimeSheetOfDeductionItem;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.holidaypriorityorder.CompanyHolidayPriorityOrder;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.holidaypriorityorder.HolidayPriorityOrder;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * 時間休暇相殺項目
 * 
 * @author daiki_ichioka
 * 
 */
@Getter
public abstract class TimeVacationOffSetItem extends CalculationTimeSheet {

	/** 控除相殺時間 */
	@Setter
	protected Optional<DeductionOffSetTime> deductionOffSetTime = Optional.empty();

	/**
	 * Constructor
	 * 
	 * @param timeSheet 時間帯(丸め付き)
	 * @param calcrange 計算範囲
	 */
	public TimeVacationOffSetItem(TimeSpanForDailyCalc timeSheet, TimeRoundingSetting rounding) {
		super(timeSheet, rounding);
	}

	/**
	 * Constructor
	 * 
	 * @param timeSheet         時間帯(丸め付き)
	 * @param calcrange         計算範囲
	 * @param midNighttimeSheet 深夜時間帯
	 */
	public TimeVacationOffSetItem(TimeSpanForDailyCalc timeSheet, TimeRoundingSetting rounding,
			List<TimeSheetOfDeductionItem> recorddeductionTimeSheets,
			List<TimeSheetOfDeductionItem> deductionTimeSheets) {
		super(timeSheet, rounding, recorddeductionTimeSheets, deductionTimeSheets);
	}

	/*
	 * 「控除する」「丸めを行う」固定でcalcTotalTImeを呼ぶ
	 */
	public AttendanceTime calcTotalTime() {
		return this.calcTotalTime(NotUseAtr.USE, NotUseAtr.USE);
	}

	/**
	 * 時間の計算（相殺控除あり）(メモ：再帰)
	 * 
	 * @param deductionOffSetTimeAtr 相殺された時間を控除する
	 * @param roundAtr 丸めを行う
	 * @return AttendanceTime 勤怠時間
	 */
	public AttendanceTime calcTotalTime(NotUseAtr deductionOffSetTimeAtr, NotUseAtr roundAtr) {
		
		int calcTime = this.timeSheet.getTimeSpan().lengthAsMinutes();
		
		// 全ての控除時間を控除する
		calcTime -= this.deductionTimeSheet.stream()
				.mapToInt(d -> d.calcTotalTime(deductionOffSetTimeAtr, roundAtr).valueAsMinutes())
				.sum();
		
		// 相殺時間を控除する
		if (deductionOffSetTimeAtr == NotUseAtr.USE && this.deductionOffSetTime.isPresent()) {
			calcTime -= this.deductionOffSetTime.get().getTotalOffSetTime();
		}
		
		// 丸め処理
		if (roundAtr == NotUseAtr.USE) {
			this.rounding.round(calcTime);
		}
		return new AttendanceTime(calcTime);
	}
	
	/**
	 * 相殺する
	 * @param priorityOrder 時間休暇相殺優先順位
	 * @param useTime 日別勤怠の時間休暇使用時間
	 * @param deductionOffSetTimeAtr 相殺された時間を控除する
	 * @return 控除相殺時間
	 */
	public DeductionOffSetTime offsetProcess(CompanyHolidayPriorityOrder priorityOrder,
			TimevacationUseTimeOfDaily useTime, NotUseAtr deductionOffSetTimeAtr) {
		//残時間
		AttendanceTime remainingTime = this.calcTotalTime(deductionOffSetTimeAtr, NotUseAtr.NOT_USE);
		return DeductionOffSetTime.create(priorityOrder, useTime, remainingTime);
	}
}
