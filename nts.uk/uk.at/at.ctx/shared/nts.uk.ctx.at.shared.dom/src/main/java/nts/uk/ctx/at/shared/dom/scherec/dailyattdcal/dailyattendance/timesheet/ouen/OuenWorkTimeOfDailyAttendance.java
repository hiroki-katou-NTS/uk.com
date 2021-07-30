package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen;

import java.util.List;
import java.util.Optional;

import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainObject;
import nts.uk.ctx.at.shared.dom.common.amount.AttendanceAmountDaily;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.DailyRecordToAttendanceItemConverter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.calculationsettings.totalrestrainttime.CalculateOfTotalConstraintTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.ManageReGetClass;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.PredetermineTimeSetForCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.personcostcalc.premiumitem.WorkingHoursUnitPrice;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.BonusPayAutoCalcSet;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;

@Getter
/** 日別勤怠の応援作業時間 */
public class OuenWorkTimeOfDailyAttendance implements DomainObject {

	/** 応援勤務枠No: 応援勤務枠No */
	private SupportFrameNo workNo;
	
	/** 勤務時間: 時間帯別勤怠の時間 */
	private OuenAttendanceTimeEachTimeSheet workTime;

	/** 移動時間: 応援別勤務の移動時間 */
	private OuenMovementTimeEachTimeSheet moveTime;
	
	/** 金額: 勤怠日別金額 */
	private AttendanceAmountDaily amount;
	
	/** 単価: 単価 */
	private WorkingHoursUnitPrice priceUnit;

	private OuenWorkTimeOfDailyAttendance(SupportFrameNo workNo, OuenAttendanceTimeEachTimeSheet workTime,
			OuenMovementTimeEachTimeSheet moveTime, AttendanceAmountDaily amount, WorkingHoursUnitPrice priceUnit) {
		super();
		this.workNo = workNo;
		this.workTime = workTime;
		this.moveTime = moveTime;
		this.amount = amount;
		this.priceUnit = priceUnit;
	}
	
	public static OuenWorkTimeOfDailyAttendance create(SupportFrameNo workNo,
			OuenAttendanceTimeEachTimeSheet workTime,
			OuenMovementTimeEachTimeSheet moveTime, AttendanceAmountDaily amount, WorkingHoursUnitPrice priceUnit) {
		
		return new OuenWorkTimeOfDailyAttendance(workNo, workTime, moveTime, amount, priceUnit);
	}
	
	/**
	 * 日別勤怠の応援作業時間を計算する
	 * @param scheduleReGetClass 予定
	 * @param recordReGetClass 実績
	 * @param workType 勤務種類
	 * @param recordWorkTimeCode 就業時間帯コード
	 * @param predetermineTimeSetByPersonInfo 所定時間設定(計算用クラス)
	 * @param bonusPayAutoCalcSet 加給自動計算設定
	 * @param calculateOfTotalConstraintTime  総拘束時間の計算
	 * @param converter コンバーター
	 * @param allTimeSheets 全ての応援作業時間帯
	 * @param processingTimeSheet 処理中の応援作業時間帯
	 * @return 日別勤怠の応援作業時間
	 */
	public static OuenWorkTimeOfDailyAttendance create(
			ManageReGetClass scheduleReGetClass,
			ManageReGetClass recordReGetClass,
			WorkType workType,
			Optional<WorkTimeCode> recordWorkTimeCode,
			Optional<PredetermineTimeSetForCalc> predetermineTimeSetByPersonInfo,
			BonusPayAutoCalcSet bonusPayAutoCalcSet,
			CalculateOfTotalConstraintTime calculateOfTotalConstraintTime,
			DailyRecordToAttendanceItemConverter converter,
			List<OuenWorkTimeSheetOfDailyAttendance> allTimeSheets,
			OuenWorkTimeSheetOfDailyAttendance processingTimeSheet) {
		
		//勤務時間を計算する
		OuenAttendanceTimeEachTimeSheet attendanceTime = OuenAttendanceTimeEachTimeSheet.create(
				scheduleReGetClass,
				recordReGetClass,
				workType,
				recordWorkTimeCode,
				predetermineTimeSetByPersonInfo,
				bonusPayAutoCalcSet,
				calculateOfTotalConstraintTime,
				converter,
				processingTimeSheet);
		
		//移動時間を計算する
		OuenMovementTimeEachTimeSheet movementTime = OuenMovementTimeEachTimeSheet.create(
				scheduleReGetClass,
				recordReGetClass,
				workType,
				recordWorkTimeCode,
				predetermineTimeSetByPersonInfo,
				bonusPayAutoCalcSet,
				calculateOfTotalConstraintTime,
				converter,
				allTimeSheets,
				processingTimeSheet);
		
		//単価を取得する
		WorkingHoursUnitPrice priceUnit = WorkingHoursUnitPrice.ZERO;
		
		//金額を計算する
		AttendanceAmountDaily amount = calcIncentiveAmount(
				priceUnit,
				attendanceTime.getTotalTime().minusMinutes(attendanceTime.getTotalPremiumTime().valueAsMinutes()),
				attendanceTime.getTotalPremiumTime());
		
		return new OuenWorkTimeOfDailyAttendance(processingTimeSheet.getWorkNo(), attendanceTime, movementTime, amount, priceUnit);
	}
	
	/**
	 * 金額を計算する
	 * @param priceUnit 作業単価
	 * @param within 所定内時間
	 * @param outside 所定外時間
	 * @return 金額
	 */
	private static AttendanceAmountDaily calcIncentiveAmount(WorkingHoursUnitPrice priceUnit, AttendanceTime within, AttendanceTime outside) {
		return new AttendanceAmountDaily(priceUnit.v() * (within.valueAsMinutes() + outside.valueAsMinutes()));
	}
}
