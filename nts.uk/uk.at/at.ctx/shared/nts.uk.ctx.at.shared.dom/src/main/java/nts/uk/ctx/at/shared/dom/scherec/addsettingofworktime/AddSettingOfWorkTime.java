/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Optional;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.DomainObject;
import nts.gul.serialize.binary.SerializableWithOptional;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingWork;
import nts.uk.ctx.at.shared.dom.worktime.common.LateEarlyAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneLateEarlySet;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;

/**
 * 就業時間の加算設定
 */
@NoArgsConstructor
@Getter
public class AddSettingOfWorkTime extends DomainObject implements SerializableWithOptional{

	private static final long serialVersionUID = 1L;
	
	/** 実働のみで計算する */
	private CalcurationByActualTimeAtr calculateActualOperation = CalcurationByActualTimeAtr.CALCULATION_BY_ACTUAL_TIME;
	/** 控除時間の扱い */
	private Optional<TreatDeductTimeForCalcWorkTime> treatDeduct = Optional.empty();
	/** 休暇時間の扱い */
	private Optional<TreatVacationTimeForCalcWorkTime> treatVacation = Optional.empty();

	private void writeObject(ObjectOutputStream stream){
		writeObjectWithOptional(stream);
	}
	
	private void readObject(ObjectInputStream stream){
		readObjectWithOptional(stream);
	}

	/**
	 * Constructor
	 * @param calculateActualOperation 実働のみで計算する
	 * @param treatDeduct 控除時間の扱い
	 * @param treatVacation 休暇時間の扱い
	 */
	public AddSettingOfWorkTime(
			CalcurationByActualTimeAtr calculateActualOperation,
			Optional<TreatDeductTimeForCalcWorkTime> treatDeduct,
			Optional<TreatVacationTimeForCalcWorkTime> treatVacation) {
		super();
		this.calculateActualOperation = calculateActualOperation;
		this.treatDeduct = treatDeduct;
		this.treatVacation = treatVacation;
	}
	
	/**
	 * Factory
	 * @param calculateActualOperation 実働のみで計算する
	 * @param treatDeduct 控除時間の扱い
	 * @param treatVacation 休暇時間の扱い
	 * @return 就業時間の加算設定
	 */
	public static AddSettingOfWorkTime createFromJavaType(
			int calculateActualOperation,
			Optional<TreatDeductTimeForCalcWorkTime> treatDeduct,
			Optional<TreatVacationTimeForCalcWorkTime> treatVacation) {
		
		AddSettingOfWorkTime myClass = new AddSettingOfWorkTime();
		myClass.calculateActualOperation = CalcurationByActualTimeAtr.valueOf(calculateActualOperation);
		myClass.treatDeduct = treatDeduct;
		myClass.treatVacation = treatVacation;
		return myClass;
	}
	
	/**
	 * 遅刻早退を就業時間に含めない設定に変更する
	 * @return 就業時間の加算設定
	 */
	public AddSettingOfWorkTime changeNotInclude() {
		if (!this.treatDeduct.isPresent()) return this;
		return new AddSettingOfWorkTime(
				this.calculateActualOperation,
				Optional.of(this.treatDeduct.get().changeNotInclude()),
				this.treatVacation);
	}
	
	/**
	 * 「実働時間のみで計算する」に変更して作成する
	 * @return 「実働時間のみで計算する」に変更したインスタンス
	 */
	public AddSettingOfWorkTime createCalculationByActualTime() {
		return new AddSettingOfWorkTime(
				CalcurationByActualTimeAtr.CALCULATION_BY_ACTUAL_TIME,
				Optional.empty(),
				Optional.empty());
	}
	
	/**
	 * 控除判断処理
	 * @param deductTime 控除時間
	 * @param lateEarlyAtr 控除区分
	 * @param timeLeavingWork 出退勤
	 * @param workType 勤務種類
	 * @param lateEarlySet 就業時間帯の遅刻・早退設定
	 * @return true:控除する,false:控除しない
	 */
	public boolean decisionLateDeductSetting(
			AttendanceTime deductTime,
			LateEarlyAtr lateEarlyAtr,
			TimeLeavingWork timeLeavingWork,
			WorkType workType,
			WorkTimezoneLateEarlySet lateEarlySet) {

		// 控除時間の扱いを確認する
		if (!this.treatDeduct.isPresent()) return true;		// 控除するを返す
		// 控除判断処理
		return this.treatDeduct.get().getTreatLateEarlyTimeSet().decisionLateDeductSetting(
				deductTime, lateEarlyAtr, timeLeavingWork, workType, lateEarlySet);
	}
	
	/**
	 * 月次法定内のみ加算するかどうか確認する
	 * @return true:法定内のみ加算,false:全て加算
	 */
	public boolean isAdditionWithinMonthlyStatutory() {
		if (this.calculateActualOperation.isCalclationByActualTime()) return false;
		if (!this.treatVacation.isPresent()) return false;
		return this.treatVacation.get().isAdditionWithinMonthlyStatutory();
	}
	
	/**
	 * 控除時間の扱いを取得する
	 * @return 労働時間計算の控除時間の扱い
	 */
	private TreatDeductTimeForCalcWorkTime getTreatDeductForCalc(){
		if (this.calculateActualOperation.isCalclationByActualTime() ||
			!this.treatDeduct.isPresent()) return TreatDeductTimeForCalcWorkTime.getCalcByActualSetting();
		return this.treatDeduct.get();
	}
	
	/**
	 * 遅刻・早退を就業時間に含めるか判断する
	 * @param lateEarlySet 就業時間帯の遅刻早退設定
	 * @return true:含める,false:含めない
	 */
	public boolean isIncludeLateEarlyInWorkTime(Optional<WorkTimezoneLateEarlySet> lateEarlySet) {
		return this.getTreatDeductForCalc().getTreatLateEarlyTimeSet().isIncludeLateEarlyInWorkTime(lateEarlySet);
	}
	
	/**
	 * 育児介護時間を含めて計算するか判断する
	 * @return true：含める、false：含めない
	 */
	public boolean isCalculateIncludCareTime() {
		return this.getTreatDeductForCalc().isCalculateIncludCareTime();
	}
	
	/**
	 * インターバル免除時間を含めて計算するか判断する
	 * @return true：含める、false：含めない
	 */
	public boolean isCalculateIncludIntervalExemptionTime() {
		return this.getTreatDeductForCalc().isCalculateIncludIntervalExemptionTime();
	}
	
	/**
	 * 休暇分を就業時間に含めるか判断する
	 * @return true：含める、false：含めない
	 */
	public boolean isCalculateIncludVacation() {
		if (this.calculateActualOperation.isCalclationByActualTime() ||
				!this.treatVacation.isPresent()) 
			return false;
		
		return this.treatVacation.get().isCalculateIncludVacation();
	}
	
	/**
	 * 欠勤をマイナスせず所定から控除する
	 * @return true：控除する、false：控除しない
	 */
	public boolean isMinusAbsenceTime() {
		if(this.calculateActualOperation.isCalclationByActualTime()) {
			return false;
		}
		if(!this.treatVacation.isPresent()) {
			return false;
		}
		return this.treatVacation.get().isMinusAbsenceTime();
	}
}

