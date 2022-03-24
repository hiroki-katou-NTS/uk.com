package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation;

import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import lombok.val;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.TimevacationUseTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.CalculationTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.deductiontime.TimeSheetOfDeductionItem;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.holidaypriorityorder.CompanyHolidayPriorityOrder;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * 時間休暇相殺項目
 * @author daiki_ichioka
 */
@Getter
public abstract class TimeVacationOffSetItem extends CalculationTimeSheet {

	/** 控除相殺時間 */
	@Setter
	protected Optional<TimevacationUseTimeOfDaily> deductionOffSetTime = Optional.empty();

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

	/**
	 * コンストラクタ
	 * @param timeSheet 時間帯
	 * @param rounding 丸め設定
	 * @param recorddeductionTimeSheets 計上用控除時間帯
	 * @param deductionTimeSheets 控除用控除時間帯
	 * @param deductionOffsetTime 控除相殺時間
	 */
	public TimeVacationOffSetItem(TimeSpanForDailyCalc timeSheet, TimeRoundingSetting rounding,
			List<TimeSheetOfDeductionItem> recorddeductionTimeSheets,
			List<TimeSheetOfDeductionItem> deductionTimeSheets,
			Optional<TimevacationUseTimeOfDaily> deductionOffsetTime) {
		super(timeSheet, rounding, recorddeductionTimeSheets, deductionTimeSheets);
		this.deductionOffSetTime = deductionOffsetTime;
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
			calcTime -= this.deductionOffSetTime.get().totalVacationAddTime();
		}
		
		// 丸め処理
		if (roundAtr == NotUseAtr.USE) {
			calcTime = this.rounding.round(calcTime);
		}
		return new AttendanceTime(calcTime);
	}

	/**
	 * 相殺時間休暇使用時間の算算
	 * 
	 * @param priorityOrder 時間休暇相殺優先順位
	 * @return AttendanceTime 勤怠時間
	 */
	public TimevacationUseTimeOfDaily calcTotalOffsetTimeVacationUseTime(CompanyHolidayPriorityOrder priorityOrder,
			TimevacationUseTimeOfDaily useTime) {
		
		/** ○控除時間の計算 */
		val deductTime = this.deductionTimeSheet.stream()
				.mapToInt(d -> d.calcTotalTime(NotUseAtr.NOT_USE, NotUseAtr.NOT_USE).valueAsMinutes())
				.sum();
		
		// 全ての控除時間を控除する
		val calcTime = this.timeSheet.getTimeSpan().lengthAsMinutes() - deductTime;
		
		/** パラメータ。時間休暇使用時間で相殺時間休暇使用を作る */
		return useTime.create(priorityOrder, new AttendanceTime(calcTime));
	}
	
	/**
	 * 相殺する
	 * @param priorityOrder 時間休暇相殺優先順位
	 * @param useTime 時間休暇使用時間
	 * @param deductionOffSetTimeAtr 相殺時間控除区分
	 */
	public TimevacationUseTimeOfDaily offsetProcess(
			CompanyHolidayPriorityOrder priorityOrder,
			TimevacationUseTimeOfDaily useTime,
			NotUseAtr deductionOffSetTimeAtr) {
		
		// 時間休暇残時間
		AttendanceTime remain = this.calcTotalTime(deductionOffSetTimeAtr, NotUseAtr.NOT_USE);
		// 控除相殺時間を作る
		this.deductionOffSetTime = Optional.of(useTime.create(priorityOrder, remain));
		// 時間休暇使用時間（相殺後）を返す
		return useTime.minus(this.deductionOffSetTime.get());
	}
}
