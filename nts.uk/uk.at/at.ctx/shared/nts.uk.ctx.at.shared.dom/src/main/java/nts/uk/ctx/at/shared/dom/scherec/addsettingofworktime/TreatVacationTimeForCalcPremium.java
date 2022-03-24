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
 * 割増時間計算時の休暇時間の扱い
 */
@NoArgsConstructor
@Getter
public class TreatVacationTimeForCalcPremium extends IncludeVacationSetForCalcWorkTime implements SerializableWithOptional{

	private static final long serialVersionUID = 1L;
	
	/** フレックスの所定超過時 */
	private Optional<CalulationMethodWhenFlexWorkHoursExceededThePrescribedTime> predeterminedExcessTimeOfFlex;

	private void writeObject(ObjectOutputStream stream){
		writeObjectWithOptional(stream);
	}
	
	private void readObject(ObjectInputStream stream){
		readObjectWithOptional(stream);
	}

	/**
	 * Instantiates a new include holidays premium calc detail set.
	 * @param addition the addition
	 * @param deformationExceedsPredeterminedValue the deformation exceeds predetermined value
	 * @param predeterminedExcessTimeOfFlex the predetermined excess time of flex
	 */
	public TreatVacationTimeForCalcPremium(
			Integer addition,
			Integer deformationExceedsPredeterminedValue,
			Integer predeterminedExcessTimeOfFlex) {
		super();
		this.addition = NotUseAtr.valueOf(addition);
		this.deformationExceedsPredeterminedValue = Optional.ofNullable(CalculationMethodForNormalWorkAndDeformedLaborOverTime.valueOf(deformationExceedsPredeterminedValue));
		this.predeterminedExcessTimeOfFlex = Optional.ofNullable(CalulationMethodWhenFlexWorkHoursExceededThePrescribedTime.valueOf(predeterminedExcessTimeOfFlex));
	}
	
	/**
	 * constructor
	 * @param addition
	 * @param deformationExceedsPredeterminedValue
	 * @param predeterminedExcessTimeOfFlex
	 */
	public TreatVacationTimeForCalcPremium(
			NotUseAtr addition,
			Optional<CalculationMethodForNormalWorkAndDeformedLaborOverTime> deformationExceedsPredeterminedValue,
			Optional<CalulationMethodWhenFlexWorkHoursExceededThePrescribedTime> predeterminedExcessTimeOfFlex) {
		super();
		this.addition = addition;
		this.deformationExceedsPredeterminedValue = deformationExceedsPredeterminedValue;
		this.predeterminedExcessTimeOfFlex = predeterminedExcessTimeOfFlex;
	}
	
	/**
	 * 「休暇分を含める就業計算詳細設定」を「休暇分を含める割増計算詳細設定」に変換する
	 * ※「フレックス勤務の所定時間超過時の計算方法」は自身の値を使う為、注意
	 * @param employmentCalcDetailedSet 休暇分を含める就業計算詳細設定
	 * @return 休暇分を含める割増計算詳細設定
	 */
	public TreatVacationTimeForCalcPremium of(
			TreatVacationTimeForCalcWorkTime employmentCalcDetailedSet) {
		return new TreatVacationTimeForCalcPremium(
				employmentCalcDetailedSet.getAddition(),
				employmentCalcDetailedSet.getDeformationExceedsPredeterminedValue(),
				this.predeterminedExcessTimeOfFlex);
	}
}

