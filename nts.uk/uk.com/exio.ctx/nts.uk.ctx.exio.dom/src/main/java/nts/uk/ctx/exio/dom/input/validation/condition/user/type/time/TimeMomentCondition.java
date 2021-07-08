package nts.uk.ctx.exio.dom.input.validation.condition.user.type.time;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.exio.dom.input.DataItem;
import nts.uk.ctx.exio.dom.input.validation.condition.user.ComparableValidation;
import nts.uk.ctx.exio.dom.input.validation.condition.user.CompareValueCondition;
import nts.uk.ctx.exio.dom.input.validation.condition.user.Validation;

/**
 * 時刻条件
 */
@Getter
@AllArgsConstructor
public class TimeMomentCondition implements ComparableValidation<ImportingConditionTimeMoment>{

	CompareValueCondition condition;
	Optional<ImportingConditionTimeMoment> value1;
	Optional<ImportingConditionTimeMoment> value2;

	@Override
	public ImportingConditionTimeMoment getTargetValue(DataItem targetItem) {
		return new ImportingConditionTimeMoment(targetItem.getInt().intValue());
	}
	
	/**
	 * 時刻クラスへの変換 
	 */
	public static Validation create(Integer value1, Integer value2, int conditionNo) {
		Optional<ImportingConditionTimeMoment> result1 = value1 == null 
				? Optional.empty() 
				: Optional.of(new ImportingConditionTimeMoment(value1));
		
		Optional<ImportingConditionTimeMoment> result2 = value2 == null 
						? Optional.empty() 
						: Optional.of(new ImportingConditionTimeMoment(value2));		
				
		return new TimeMomentCondition(
				CompareValueCondition.values()[conditionNo],
				result1,
				result2);
	}
}
