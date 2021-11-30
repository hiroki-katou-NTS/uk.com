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
import nts.gul.serialize.binary.SerializableWithOptional;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * 就業時間計算時の休暇時間の扱い
 */
@NoArgsConstructor
@Getter
public class TreatVacationTimeForCalcWorkTime extends IncludeVacationSetForCalcWorkTime implements SerializableWithOptional{

	private static final long serialVersionUID = 1L;
	
	/** 月次法定内のみ加算 */
	private Optional<NotUseAtr> additionWithinMonthlyStatutory;
	/** フレックスの所定不足時 */
	private Optional<CalculationMethodAtTheTimeOfLackOfFixedTimeForFlexWork> predeterminedDeficiencyOfFlex;
	/** 欠勤時間をマイナスする */
	private Optional<NotUseAtr> minusAbsenceTime;

	private void writeObject(ObjectOutputStream stream){
		writeObjectWithOptional(stream);
	}
	
	private void readObject(ObjectInputStream stream){
		readObjectWithOptional(stream);
	}

	/**
	 * Instantiates a new employment calc detailed set include vacation amount.
	 * @param addition the addition
	 * @param deformationExceedsPredeterminedValue the deformation exceeds predetermined value
	 * @param additionWithinMonthlyStatutory the addition within monthly statutory
	 * @param predeterminedDeficiencyOfFlex the predetermined deficiency of flex
	 * @param minusAbsenceTime the minus absence time
	 */
	public TreatVacationTimeForCalcWorkTime(Integer addition,
			Integer deformationExceedsPredeterminedValue,
			Integer additionWithinMonthlyStatutory,
			Integer predeterminedDeficiencyOfFlex,
			Integer minusAbsenceTime) {
		super();
		this.addition = NotUseAtr.valueOf(addition);
		this.deformationExceedsPredeterminedValue = Optional.ofNullable(CalculationMethodForNormalWorkAndDeformedLaborOverTime.valueOf(deformationExceedsPredeterminedValue));
		this.additionWithinMonthlyStatutory = Optional.ofNullable(NotUseAtr.valueOf(additionWithinMonthlyStatutory));
		this.predeterminedDeficiencyOfFlex = Optional.ofNullable(CalculationMethodAtTheTimeOfLackOfFixedTimeForFlexWork.valueOf(predeterminedDeficiencyOfFlex));
		this.minusAbsenceTime = Optional.ofNullable(NotUseAtr.valueOf(minusAbsenceTime));
	}
	
	/**
	 * 月次法定内のみ加算するかどうか確認する
	 * @return true:法定内のみ加算,false:全て加算
	 */
	public boolean isAdditionWithinMonthlyStatutory() {
		return this.additionWithinMonthlyStatutory.orElse(NotUseAtr.NOT_USE).isUse();
	}
	
	/**
	 * 欠勤をマイナスせず所定から控除する
	 * @return true：控除する、false：控除しない
	 */
	public boolean isMinusAbsenceTime() {
		return this.minusAbsenceTime.orElse(NotUseAtr.NOT_USE).isUse();
	}
}
