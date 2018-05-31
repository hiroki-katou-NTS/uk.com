/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.calculation.holiday.kmk013_splitdomain;

import java.util.Optional;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.calculation.holiday.kmk013_splitdomain.ENUM.CalculationMethodForNormalWorkAndDeformedLaborOverTime;
import nts.uk.ctx.at.shared.dom.calculation.holiday.kmk013_splitdomain.ENUM.CalulationMethodWhenFlexWorkHoursExceededThePrescribedTime;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * The Class IncludeHolidaysPremiumCalcDetailSet.
 */
// 休暇分を含める割増計算詳細設定
@NoArgsConstructor
@Getter
public class IncludeHolidaysPremiumCalcDetailSet extends DomainObject{
	// 加算する
	private NotUseAtr addition;
	
	// 通常、変形の所定超過時
	private Optional<CalculationMethodForNormalWorkAndDeformedLaborOverTime> deformationExceedsPredeterminedValue;
	
	// フレックスの所定超過時
	private Optional<CalulationMethodWhenFlexWorkHoursExceededThePrescribedTime> predeterminedExcessTimeOfFlex;

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
}

