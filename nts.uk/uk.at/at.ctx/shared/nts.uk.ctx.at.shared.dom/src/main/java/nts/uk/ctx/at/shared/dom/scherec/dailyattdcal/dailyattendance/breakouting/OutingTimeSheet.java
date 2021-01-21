package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting;

import java.util.ArrayList;
//import java.util.Collections;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;
import nts.gul.util.value.Finally;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.timerounding.Rounding;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.common.timerounding.Unit;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeActualStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.TimeSpanForDailyCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.deductiontime.DeductionClassification;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.deductiontime.TimeSheetOfDeductionItem;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.deductiontime.WorkingBreakTimeAtr;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 
 * @author nampt
 * 外出時間帯
 *
 */
@AllArgsConstructor
@Getter
@Setter
public class OutingTimeSheet extends DomainObject {
	
	/*
	 * 外出枠NO
	 */
	private OutingFrameNo outingFrameNo;
	
	// 外出: 勤怠打刻(実打刻付き) - primitive value
	private Optional<TimeActualStamp> goOut;
	
	/*
	 * 計算時間
	 */
	private AttendanceTime outingTimeCalculation;
	
	/*
	 * 外出時間
	 */
	private AttendanceTime outingTime;
	
	/*
	 * 外出理由
	 */
	private GoingOutReason reasonForGoOut;
	
	// 戻り: 勤怠打刻(実打刻付き) - primitive value
	private Optional<TimeActualStamp> comeBack;

	public void setProperty(OutingFrameNo outingFrameNo, Optional<TimeActualStamp> goOut, AttendanceTime outingTimeCalculation,
			AttendanceTime outingTime,  GoingOutReason reasonForGoOut, Optional<TimeActualStamp> comeBack) {
		this.outingFrameNo = outingFrameNo;
		this.goOut = goOut;
		this.outingTimeCalculation = outingTimeCalculation;
		this.outingTime = outingTime;
		this.reasonForGoOut = reasonForGoOut;
		this.comeBack = comeBack;
		
	}
	
	/**
	 * 自分自身を控除項目の時間帯に変換する
	 * @return 控除項目の時間帯
	 */
	public TimeSheetOfDeductionItem toTimeSheetOfDeductionItem() {
		return TimeSheetOfDeductionItem.createTimeSheetOfDeductionItemAsFixed(new TimeSpanForDailyCalc(this.goOut.get().getStamp().get().getTimeDay()
																											.getTimeWithDay().orElse(TimeWithDayAttr.THE_PRESENT_DAY_0000), 
																										this.comeBack.get().getStamp().get().getTimeDay()
																											.getTimeWithDay().orElse(TimeWithDayAttr.THE_PRESENT_DAY_0000)),
																			  new TimeRoundingSetting(Unit.ROUNDING_TIME_1MIN, Rounding.ROUNDING_DOWN),
																			  new ArrayList<>(),
																			  new ArrayList<>(),
																			  WorkingBreakTimeAtr.NOTWORKING,
																			  Finally.of(this.reasonForGoOut),
																			  Finally.empty(),
																			  Optional.empty(),
																			  DeductionClassification.GO_OUT,
																			  Optional.empty()
																			  );
	}
	
	/**
	 * 自信が計算できる状態か判定うる
	 * @return 計算可能である
	 */
	public boolean isCalcState() {
		return isCalcGoOut() && isCalcComeBack();
	}
	
	/**
	 * 外出時刻が計算できる状態になっているか判定する
	 * (nullになっていないか)
	 * @return 計算可能である。
	 */
	private boolean isCalcGoOut() {
		if(this.getGoOut() != null && this.getGoOut().isPresent()) {
			return this.getGoOut().get().isCalcStampState();
		}
		return false;
	}
	
	/**
	 * 戻り時刻が計算できる状態になっているか判定する
	 * (null になっていないか)
	 * @return　 計算可能である。
	 */
	private boolean isCalcComeBack() {
		if(this.getComeBack() != null && this.getComeBack().isPresent()) {
			return this.getGoOut().get().isCalcStampState();
		}
		return false;		
	}
}
