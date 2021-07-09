package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainObject;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.DailyRecordToAttendanceItemConverter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.calculationsettings.totalrestrainttime.CalculateOfTotalConstraintTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime.AttendanceTimeOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.TimeSpanForDailyCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.ManageReGetClass;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.PredetermineTimeSetForCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.BonusPayAutoCalcSet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.premiumtime.PremiumTime;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;

@Getter
/** 時間帯別勤怠の時間 */
public class OuenAttendanceTimeEachTimeSheet implements DomainObject {

	/** 総労働時間: 勤怠時間 */
	private AttendanceTime totalTime;
	
	/** 休憩時間: 勤怠時間 */
	private AttendanceTime breakTime;
	
	/** 所定内時間: 勤怠時間 */
	private AttendanceTime withinTime;
	
	/** 割増時間: 割増時間 */
	private List<PremiumTime> premiumTime;
	
	/** 医療時間: 時間帯別勤怠の医療時間 */
	private List<MedicalCareTimeEachTimeSheet> medicalTime;

	private OuenAttendanceTimeEachTimeSheet(AttendanceTime totalTime, AttendanceTime breakTime,
			AttendanceTime withinTime, List<MedicalCareTimeEachTimeSheet> medicalTime,
			List<PremiumTime> premiumTime) {
		super();
		this.totalTime = totalTime;
		this.breakTime = breakTime;
		this.withinTime = withinTime;
		this.medicalTime = medicalTime;
		this.premiumTime = premiumTime;
	}
	
	public static OuenAttendanceTimeEachTimeSheet create(AttendanceTime totalTime, 
			AttendanceTime breakTime, AttendanceTime withinTime, 
			List<MedicalCareTimeEachTimeSheet> medicalTime, List<PremiumTime> premiumTime) {
		
		return new OuenAttendanceTimeEachTimeSheet(totalTime, breakTime, withinTime, medicalTime, premiumTime);
	}
	
	/**
	 * 時間帯別勤怠の時間を作成する
	 * @param scheduleReGetClass 予定
	 * @param recordReGetClass 実績
	 * @param workType 勤務種類
	 * @param recordWorkTimeCode 就業時間帯コード
	 * @param predetermineTimeSetByPersonInfo 所定時間設定(計算用クラス)
	 * @param bonusPayAutoCalcSet 加給自動計算設定
	 * @param calculateOfTotalConstraintTime  総拘束時間の計算
	 * @param converter コンバーター
	 * @param ouenWorkTimeSheet 応援作業時間帯
	 * @return 時間帯別勤怠の時間
	 */
	public static OuenAttendanceTimeEachTimeSheet create(
			ManageReGetClass scheduleReGetClass,
			ManageReGetClass recordReGetClass,
			WorkType workType,
			Optional<WorkTimeCode> recordWorkTimeCode,
			Optional<PredetermineTimeSetForCalc> predetermineTimeSetByPersonInfo,
			BonusPayAutoCalcSet bonusPayAutoCalcSet,
			CalculateOfTotalConstraintTime calculateOfTotalConstraintTime,
			DailyRecordToAttendanceItemConverter converter,
			OuenWorkTimeSheetOfDailyAttendance ouenWorkTimeSheet) {
		
		//作業時間帯の開始終了を取得する
		Optional<TimeSpanForDailyCalc> startEnd = ouenWorkTimeSheet.getTimeSheet().getStartAndEnd();
		
		if(!startEnd.isPresent())
			return OuenAttendanceTimeEachTimeSheet.createAllZero();
		
		//日別実績(Work)の退避
		IntegrationOfDaily copyIntegrationOfDaily = converter.setData(recordReGetClass.getIntegrationOfDaily()).toDomain();
		
		//時間帯を変更して日別勤怠の勤怠時間を計算する
		AttendanceTimeOfDailyAttendance calcResult = AttendanceTimeOfDailyAttendance.calcAfterChangedRange(
				scheduleReGetClass,
				recordReGetClass,
				workType,
				recordWorkTimeCode,
				predetermineTimeSetByPersonInfo,
				bonusPayAutoCalcSet,
				calculateOfTotalConstraintTime,
				converter,
				startEnd.get());
		
		copyIntegrationOfDaily.setAttendanceTimeOfDailyPerformance(Optional.of(calcResult));
		
		//編集状態を取得（日別実績の編集状態が持つ勤怠項目IDのみのList作成）
		List<Integer> attendanceItemIdList = recordReGetClass.getIntegrationOfDaily().getEditState().stream()
				.map(editState -> editState.getAttendanceItemId())
				.distinct()
				.collect(Collectors.toList());

		//手修正項目を戻した後の計算処理
		IntegrationOfDaily result = AttendanceTimeOfDailyAttendance.reCalcForSupport(
				copyIntegrationOfDaily,
				converter,
				attendanceItemIdList,
				recordReGetClass);
		
		if(!result.getAttendanceTimeOfDailyPerformance().isPresent())
			return OuenAttendanceTimeEachTimeSheet.createAllZero();
		
		//項目移送
		return valueOf(result.getAttendanceTimeOfDailyPerformance().get());
	}
	
	/**
	 * 全て0で作成する
	 * @return
	 */
	public static OuenAttendanceTimeEachTimeSheet createAllZero() {
		return new OuenAttendanceTimeEachTimeSheet(AttendanceTime.ZERO, AttendanceTime.ZERO, AttendanceTime.ZERO,
				Collections.emptyList(), Collections.emptyList());
	}
	
	/**
	 * 割増時間を合計する
	 * @return 割増時間の合計
	 */
	public AttendanceTime getTotalPremiumTime() {
		return new AttendanceTime(this.premiumTime.stream().mapToInt(time -> time.getPremitumTime().valueAsMinutes()).sum());
	}
	
	/**
	 * 日別勤怠の勤怠時間から作成する
	 * @param attendanceTime 日別勤怠の勤怠時間
	 * @return 時間帯別勤怠の時間
	 */
	private static OuenAttendanceTimeEachTimeSheet valueOf(AttendanceTimeOfDailyAttendance attendanceTime) {
		return new OuenAttendanceTimeEachTimeSheet(
				attendanceTime.getActualWorkingTimeOfDaily().getTotalWorkingTime().getTotalTime(),
				attendanceTime.getActualWorkingTimeOfDaily().getTotalWorkingTime().getBreakTimeOfDaily().getToRecordTotalTime().getTotalTime().getTime(),
				attendanceTime.getActualWorkingTimeOfDaily().getTotalWorkingTime().getWithinStatutoryTimeOfDaily().getActualWorkTime().addMinutes(
						attendanceTime.getActualWorkingTimeOfDaily().getTotalWorkingTime().getWithinStatutoryTimeOfDaily().getActualWithinPremiumTime().minute()),
				MedicalCareTimeEachTimeSheet.createAllZero(),//様式9が未実装の為、全て0
				attendanceTime.getActualWorkingTimeOfDaily().getPremiumTimeOfDailyPerformance().getPremiumTimes());
	}
}
