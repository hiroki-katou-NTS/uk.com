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
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * The Class IncludeHolidaysPremiumCalcDetailSet.
 */
// 休暇分を含める割増計算詳細設定
@NoArgsConstructor
@Getter
public class IncludeHolidaysPremiumCalcDetailSet extends DomainObject implements SerializableWithOptional{

	/** Serializable */
	private static final long serialVersionUID = 1L;
	
	// 加算する
	private NotUseAtr addition;
	
	// 通常、変形の所定超過時
	private Optional<CalculationMethodForNormalWorkAndDeformedLaborOverTime> deformationExceedsPredeterminedValue;
	
	// フレックスの所定超過時
	private Optional<CalulationMethodWhenFlexWorkHoursExceededThePrescribedTime> predeterminedExcessTimeOfFlex;

	private void writeObject(ObjectOutputStream stream){
		writeObjectWithOptional(stream);
	}
	
	private void readObject(ObjectInputStream stream){
		readObjectWithOptional(stream);
	}

	/**
	 * Instantiates a new include holidays premium calc detail set.
	 *
	 * @param addition the addition
	 * @param deformationExceedsPredeterminedValue the deformation exceeds predetermined value
	 * @param predeterminedExcessTimeOfFlex the predetermined excess time of flex
	 */
	public IncludeHolidaysPremiumCalcDetailSet(Integer addition,
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
	public IncludeHolidaysPremiumCalcDetailSet(
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
	public IncludeHolidaysPremiumCalcDetailSet of(EmploymentCalcDetailedSetIncludeVacationAmount employmentCalcDetailedSet) {
		return new IncludeHolidaysPremiumCalcDetailSet(
				employmentCalcDetailedSet.getAddition(),
				employmentCalcDetailedSet.getDeformationExceedsPredeterminedValue(),
				this.predeterminedExcessTimeOfFlex);
	}
}

