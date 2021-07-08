package nts.uk.ctx.exio.dom.input.validation.user.type.numeric.integer;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.exio.dom.input.DataItem;
import nts.uk.ctx.exio.dom.input.validation.user.ComparableValidation;
import nts.uk.ctx.exio.dom.input.validation.user.CompareValueCondition;
import nts.uk.ctx.exio.dom.input.validation.user.Validation;

/**
 * 整数条件
 */
@Getter
@AllArgsConstructor
public class IntegerCondition implements ComparableValidation<ImportingConditionInteger>{
	CompareValueCondition condition;
	Optional<ImportingConditionInteger> value1;
	Optional<ImportingConditionInteger> value2;

	@Override
	public ImportingConditionInteger getTargetValue(DataItem targetItem) {
		return new ImportingConditionInteger(targetItem.getInt());
	}
	
	/**
	 * 整数クラスへの変換(app infra用)
	 */
	public static Validation create(Long value1, Long value2, int conditionNo) {
		Optional<ImportingConditionInteger> result1 = value1 == null 
				? Optional.empty() 
				: Optional.of(new ImportingConditionInteger(value1));
		
		Optional<ImportingConditionInteger> result2 = value2 == null 
						? Optional.empty() 
						: Optional.of(new ImportingConditionInteger(value2));		
				
		return new IntegerCondition(
				CompareValueCondition.values()[conditionNo],
				result1,
				result2);
	}
}
