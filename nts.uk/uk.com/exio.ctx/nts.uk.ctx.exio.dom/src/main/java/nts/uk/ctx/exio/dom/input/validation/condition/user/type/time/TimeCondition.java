package nts.uk.ctx.exio.dom.input.validation.condition.user.type.time;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.exio.dom.input.DataItem;
import nts.uk.ctx.exio.dom.input.validation.condition.user.ComparableValidation;
import nts.uk.ctx.exio.dom.input.validation.condition.user.CompareValueCondition;
import nts.uk.ctx.exio.dom.input.validation.condition.user.Validation;

/**
 * 時間条件
 */
@Getter
@AllArgsConstructorpublic class TimeCondition implements ComparableValidation<ImportingConditionTime>{

	CompareValueCondition condition;
	Optional<ImportingConditionTime> value1;
	Optional<ImportingConditionTime> value2;
	
	@Override
	public ImportingConditionTime getTargetValue(DataItem targetItem) {
		return new ImportingConditionTime(targetItem.getInt().intValue());
	}
	
	/**
	 * 時間クラスへの変換 
	 */
	public static Validation create(Integer value1, Integer value2, int conditionNo) {
		Optional<ImportingConditionTime> result1 = value1 == null 
				? Optional.empty() 
				: Optional.of(new ImportingConditionTime(value1));
		
		Optional<ImportingConditionTime> result2 = value2 == null 
						? Optional.empty() 
						: Optional.of(new ImportingConditionTime(value2));		
				
		return new TimeCondition(
				CompareValueCondition.values()[conditionNo],
				result1,
				result2);
	}
}
