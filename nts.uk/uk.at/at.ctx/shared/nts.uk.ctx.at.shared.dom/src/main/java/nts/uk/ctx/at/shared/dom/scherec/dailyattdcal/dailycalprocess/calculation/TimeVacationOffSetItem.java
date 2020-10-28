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
		if (!this.deductionTimeSheet.isEmpty()) {
			calcTime -= this.deductionTimeSheet.stream()
					.mapToInt(timeSheet -> timeSheet.calcTotalTime(deductionOffSetTimeAtr, roundAtr).valueAsMinutes())
					.sum();
		} else {
			calcTime -= this.deductionTimeSheet.stream()
					.mapToInt(timeSheet -> timeSheet.getTimeSheet().getTimeSpan().lengthAsMinutes()).sum();
		}
		
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
	 * 遅刻 or 早退 or 外出 時間の休暇時間相殺
	 * 
	 * @param deductionAtr                計上 or 控除
	 * @param companyholidayPriorityOrder 時間休暇相殺優先順位
	 * @param timeVacationUseTime         日別実績の時間休暇使用時間
	 * @param deductionOffSetTime         控除相殺時間
	 */
	public void offsetProcessInPriorityOrder(DeductionAtr deductionAtr,
			CompanyHolidayPriorityOrder companyholidayPriorityOrder, TimevacationUseTimeOfDaily timeVacationUseTime,
			DeductionOffSetTime deductionOffSetTime) {
		// 時間休暇の優先順に処理
		for (HolidayPriorityOrder holiday : companyholidayPriorityOrder.getHolidayPriorityOrders()) {
			switch (holiday) {
			case ANNUAL_HOLIDAY:
				this.offsetProcess(timeVacationUseTime.getTimeAnnualLeaveUseTime(),
						this.calcTotalTime(NotUseAtr.USE, NotUseAtr.NOT_USE), // 時間の計算
						deductionOffSetTime.getAnnualLeave());
				break;

			case SUB_HOLIDAY:
				this.offsetProcess(timeVacationUseTime.getTimeCompensatoryLeaveUseTime(),
						this.calcTotalTime(NotUseAtr.USE, NotUseAtr.NOT_USE), // 時間の計算,
						deductionOffSetTime.getCompensatoryLeave());
				break;

			case SIXTYHOUR_HOLIDAY:
				this.offsetProcess(timeVacationUseTime.getSixtyHourExcessHolidayUseTime(),
						this.calcTotalTime(NotUseAtr.USE, NotUseAtr.NOT_USE), // 時間の計算,
						deductionOffSetTime.getSixtyHourHoliday());
				break;

			case SPECIAL_HOLIDAY:
				this.offsetProcess(timeVacationUseTime.getTimeSpecialHolidayUseTime(),
						this.calcTotalTime(NotUseAtr.USE, NotUseAtr.NOT_USE), // 時間の計算
						deductionOffSetTime.getSpecialHoliday());
				break;
			}
		}
	}

	/**
	 * 相殺処理
	 * 
	 * @param timevacationUseTime 時間休暇使用残時間
	 * @param remainingTime       遅刻 or 早退 or 外出 の残時間
	 * @param deductionOffSetTime 相殺控除時間
	 */
	private void offsetProcess(AttendanceTime timevacationUseTime, AttendanceTime remainingTime,
			AttendanceTime deductionOffSetTime) {
		// 相殺時間
		AttendanceTime offSetTime = AttendanceTime.ZERO;

		// 相殺する時間を計算（比較）する
		offSetTime = this.calcOffsetTime(timevacationUseTime, remainingTime);

		// 相殺した時間を相殺控除時間に格納する
		deductionOffSetTime.addMinutes(offSetTime.valueAsMinutes());

		// 相殺した時間を時間休暇使用残時間から減算する
		timevacationUseTime.minusMinutes(offSetTime.valueAsMinutes());
	}

	/**
	 * 相殺する時間を計算（比較）する
	 * 
	 * @param timeVacationUseTime 時間休暇使用時間
	 * @param remainingTime       遅刻 or 早退 or 外出 の残時間
	 * @return 相殺する時間
	 */
	private AttendanceTime calcOffsetTime(AttendanceTime timeVacationUseTime, AttendanceTime remainingTime) {
		
		int offSetTime;
		if (timeVacationUseTime.lessThanOrEqualTo(remainingTime)) {
			offSetTime = timeVacationUseTime.valueAsMinutes();
		} else {
			offSetTime = remainingTime.valueAsMinutes();
		}
		return new AttendanceTime(offSetTime);
	}
}
