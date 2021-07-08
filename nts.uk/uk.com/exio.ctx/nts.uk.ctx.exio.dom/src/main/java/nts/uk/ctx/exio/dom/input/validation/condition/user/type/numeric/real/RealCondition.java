package nts.uk.ctx.exio.dom.input.validation.condition.user.type.numeric.real;

import java.math.BigDecimal;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.exio.dom.input.DataItem;
import nts.uk.ctx.exio.dom.input.validation.condition.user.ComparableValidation;
import nts.uk.ctx.exio.dom.input.validation.condition.user.CompareValueCondition;
import nts.uk.ctx.exio.dom.input.validation.condition.user.Validation;

/**
 * 受入条件実数
 */
@Getter
@AllArgsConstructor
public class RealCondition implements ComparableValidation<ImportingConditionReal>{

	CompareValueCondition condition;
	Optional<ImportingConditionReal> value1;
	Optional<ImportingConditionReal> value2;
	
	@Override
	public ImportingConditionReal getTargetValue(DataItem targetItem) {
		return new ImportingConditionReal(targetItem.getReal());
	}

	public static Validation create(BigDecimal value1, BigDecimal value2, int conditionNo) {
		Optional<ImportingConditionReal> result1 = value1 == null 
				? Optional.empty() 
				: Optional.of(new ImportingConditionReal(value1));
		
		Optional<ImportingConditionReal> result2 = value2 == null 
						? Optional.empty() 
						: Optional.of(new ImportingConditionReal(value2));		
				
		return new RealCondition(
				CompareValueCondition.values()[conditionNo],
				result1,
				result2);
	}
}
