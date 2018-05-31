/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.calculation.holiday.kmk013_splitdomain;

import java.util.Optional;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.calculation.holiday.kmk013_splitdomain.ENUM.CalculationMethodAtTheTimeOfLackOfFixedTimeForFlexWork;
import nts.uk.ctx.at.shared.dom.calculation.holiday.kmk013_splitdomain.ENUM.CalculationMethodForNormalWorkAndDeformedLaborOverTime;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * The Class EmploymentCalcDetailedSetIncludeVacationAmount.
 */
// 休暇分を含める就業計算詳細設定
@NoArgsConstructor
@Getter
public class EmploymentCalcDetailedSetIncludeVacationAmount extends DomainObject{
	
	/** The addition. */
	// 加算する
	private NotUseAtr addition;
	
	/** The deformation exceeds predetermined value. */
	// 通常、変形の所定超過時
	private Optional<CalculationMethodForNormalWorkAndDeformedLaborOverTime> deformationExceedsPredeterminedValue;
	
	/** The addition within monthly statutory. */
	// 月次法定内のみ加算
	private Optional<NotUseAtr> additionWithinMonthlyStatutory;
	
	/** The predetermined deficiency of flex. */
	// フレックスの所定不足時
	private Optional<CalculationMethodAtTheTimeOfLackOfFixedTimeForFlexWork> predeterminedDeficiencyOfFlex;

	/**
	 * Instantiates a new employment calc detailed set include vacation amount.
	 *
	 * @param addition the addition
	 * @param deformationExceedsPredeterminedValue the deformation exceeds predetermined value
	 * @param additionWithinMonthlyStatutory the addition within monthly statutory
	 * @param predeterminedDeficiencyOfFlex the predetermined deficiency of flex
	 */
	public EmploymentCalcDetailedSetIncludeVacationAmount(Integer addition,
			Integer deformationExceedsPredeterminedValue,
			Integer additionWithinMonthlyStatutory,
			Integer predeterminedDeficiencyOfFlex) {
		super();
		this.addition = NotUseAtr.valueOf(addition);
		this.deformationExceedsPredeterminedValue = Optional.ofNullable(deformationExceedsPredeterminedValue == null 
																			? null 
																			: CalculationMethodForNormalWorkAndDeformedLaborOverTime.valueOf(deformationExceedsPredeterminedValue));
		this.additionWithinMonthlyStatutory = Optional.ofNullable(additionWithinMonthlyStatutory == null 
																			? null 
																			: NotUseAtr.valueOf(additionWithinMonthlyStatutory));
		this.predeterminedDeficiencyOfFlex = Optional.ofNullable(predeterminedDeficiencyOfFlex == null 
																			? null 
																			: CalculationMethodAtTheTimeOfLackOfFixedTimeForFlexWork.valueOf(predeterminedDeficiencyOfFlex));
	}
}

