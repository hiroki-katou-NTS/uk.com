package nts.uk.ctx.exio.dom.input.validation.condition.user.type.date;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.exio.dom.input.DataItem;
import nts.uk.ctx.exio.dom.input.validation.condition.user.ComparableValidation;
import nts.uk.ctx.exio.dom.input.validation.condition.user.CompareValueCondition;
import nts.uk.ctx.exio.dom.input.validation.condition.user.Validation;

/**
 * 日付条件
 */
@Getter
@AllArgsConstructor
public class DateCondition implements ComparableValidation<GeneralDate>{

	CompareValueCondition condition;
	Optional<GeneralDate> value1;
	Optional<GeneralDate> value2;

	@Override
	public GeneralDate getTargetValue(DataItem targetItem) {
		return targetItem.getDate();
	}
	
	/**
	 * 日付クラスへの変換 
	 */
	public static Validation create(GeneralDate value1, GeneralDate value2, int conditionNo) {
		Optional<GeneralDate> result1 = value1 == null 
				? Optional.empty() 
				: Optional.of(value1);
		
		Optional<GeneralDate> result2 = value2 == null 
						? Optional.empty() 
						: Optional.of(value2);		
				
		return new DateCondition(
				CompareValueCondition.values()[conditionNo],
				result1,
				result2);
	}
}
