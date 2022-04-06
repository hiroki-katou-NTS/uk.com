/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime;

import java.io.Serializable;
import java.util.Optional;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.PremiumAtr;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * フレックス勤務の加算設定
 */
@Getter
@NoArgsConstructor
public class WorkFlexAdditionSet extends AggregateRoot implements Serializable, AddSetting{

	private static final long serialVersionUID = 1L;

	/** 会社ID */
	private String companyId;
	/** 労働制 */
	public static int LABOR_SYSTEM_ATR = 2;
	/** 労働時間の加算設定 */
	private AddSettingOfWorkingTime addSetOfWorkingTime;	
	
	public WorkFlexAdditionSet(String companyId, AddSettingOfWorkingTime vacationCalcMethodSet) {
		super();
		this.companyId = companyId;
		this.addSetOfWorkingTime = vacationCalcMethodSet;
	}
	
	/**
	 * Factory
	 * @param companyId
	 * @param prmCalcActualOpe
	 * @param prmCalcIncludeCare
	 * @param prmIncludeLate
	 * @param prmIncludeLateByApp
	 * @param prmEnableSetPerWorkHour
	 * @param prmCalcIncludeInterval
	 * @param prmAddition
	 * @param prmExcessOfFixDefo
	 * @param prmExcessOfFlex
	 * @param wrkCalcActualOpe
	 * @param wrkCalcIncludeCare
	 * @param wrkIncludeLate
	 * @param wrkIncludeLateByApp
	 * @param wrkEnableSetPerWorkHour
	 * @param wrkCalcIncludeInterval
	 * @param wrkAddition
	 * @param wrkExcessOfFixDefo
	 * @param wrkExcessOfFlex
	 * @param wrkWithinMonth
	 * @param wrkMinusAbsence
	 * @param useAtr
	 * @return フレックス勤務の加算設定
	 */
	public static WorkFlexAdditionSet createFromJavaType(
			String companyId,
			int prmCalcActualOpe,
			Integer prmCalcIncludeCare,
			Integer prmIncludeLate,
			Integer prmIncludeLateByApp,
			Integer prmEnableSetPerWorkHour,
			Integer prmCalcIncludeInterval,
			Integer prmAddition,
			Integer prmExcessOfFixDefo,
			Integer prmExcessOfFlex,
			int wrkCalcActualOpe,
			Integer wrkCalcIncludeCare,
			Integer wrkIncludeLate,
			Integer wrkIncludeLateByApp,
			Integer wrkEnableSetPerWorkHour,
			Integer wrkCalcIncludeInterval,
			Integer wrkAddition,
			Integer wrkExcessOfFixDefo,
			Integer wrkExcessOfFlex,
			Integer wrkWithinMonth,
			Integer wrkMinusAbsence,
			int useAtr) {
		
		WorkFlexAdditionSet myClass = new WorkFlexAdditionSet();
		myClass.companyId = companyId;
		TreatDeductTimeForCalcWorkTime premiumCommonSet = null;
		TreatVacationTimeForCalcPremium premiumAdvancedSet = null;
		if (prmCalcIncludeCare != null){
			premiumCommonSet = TreatDeductTimeForCalcWorkTime.createFromJavaType(
					prmCalcIncludeCare,
					TreatLateEarlyTimeSetUnit.createFromJavaType(
							prmIncludeLate,
							prmIncludeLateByApp,
							prmEnableSetPerWorkHour),
					prmCalcIncludeInterval);
			premiumAdvancedSet = new TreatVacationTimeForCalcPremium(
					prmAddition,
					prmExcessOfFixDefo,
					prmExcessOfFlex);
		}
		TreatDeductTimeForCalcWorkTime workTimeCommonSet = null;
		TreatVacationTimeForCalcWorkTime workTimeAdvancedSet = null;
		if (wrkCalcIncludeCare != null){
			workTimeCommonSet = TreatDeductTimeForCalcWorkTime.createFromJavaType(
					wrkCalcIncludeCare,
					TreatLateEarlyTimeSetUnit.createFromJavaType(
							wrkIncludeLate,
							wrkIncludeLateByApp,
							wrkEnableSetPerWorkHour),
					wrkCalcIncludeInterval);
			workTimeAdvancedSet = new TreatVacationTimeForCalcWorkTime(
					wrkAddition,
					wrkExcessOfFixDefo,
					wrkWithinMonth,
					wrkExcessOfFlex,
					wrkMinusAbsence);
		}
		myClass.addSetOfWorkingTime = new AddSettingOfWorkingTime(
				AddSettingOfPremiumTime.createFromJavaType(
						prmCalcActualOpe,
						Optional.ofNullable(premiumCommonSet),
						Optional.ofNullable(premiumAdvancedSet)),
				AddSettingOfWorkTime.createFromJavaType(
						wrkCalcActualOpe,
						Optional.ofNullable(workTimeCommonSet),
						Optional.ofNullable(workTimeAdvancedSet)),
				NotUseAtr.valueOf(useAtr));
		return myClass;
	}
	
	public static interface Require {
		WorkFlexAdditionSet workFlexAdditionSet(String companyId);
	}
	
	/**
	 * 休暇加算するかどうか判断
	 * @param premiumAtr 割増区分
	 * @return 加算する：USE 加算しない：NOT_USE
	 */
	public NotUseAtr isAddVacation(PremiumAtr premiumAtr) {
		return this.addSetOfWorkingTime.isAddVacation(premiumAtr);
	}
	
	/**
	 * 「実働時間のみで計算する」に変更して作成する
	 * @return 「実働時間のみで計算する」に変更したインスタンス
	 */
	public WorkFlexAdditionSet createCalculationByActualTime() {
		return new WorkFlexAdditionSet(this.companyId, this.addSetOfWorkingTime.createCalculationByActualTime());
	}
	
	/**
	 * 割増時間の計算方法を就業時間の計算方法と同じにする
	 * （就業時間から控除するフレックス時間を求めるときの加算設定を取得する）
	 * @return 割増計算方法を設定する＝「しない」に変更した加算設定
	 */
	public WorkFlexAdditionSet getWorkTimeDeductFlexTime() {
		return new WorkFlexAdditionSet(this.companyId, this.addSetOfWorkingTime.getWorkTimeDeductFlexTime());
	}

	/**
	 * 遅刻、早退の控除設定を「控除する」に変更して作成する
	 * @return 遅刻、早退の控除設定を「控除する」に変更したインスタンス
	 */
	public WorkFlexAdditionSet createNewDeductLateEarly() {
		return new WorkFlexAdditionSet(this.companyId, this.addSetOfWorkingTime.createNewDeductLateEarly());
	}
}
