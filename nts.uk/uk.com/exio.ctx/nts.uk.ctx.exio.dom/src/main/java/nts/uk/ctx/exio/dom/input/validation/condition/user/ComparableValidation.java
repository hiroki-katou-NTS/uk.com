package nts.uk.ctx.exio.dom.input.validation.condition.user;


import java.util.Optional;

import nts.uk.ctx.exio.dom.input.DataItem;
import nts.uk.ctx.exio.dom.input.validation.Validation;

public interface ComparableValidation <T extends Comparable<T>> extends Validation{
	
	Optional<T> getValue1();
	Optional<T> getValue2();
	CompareValueCondition getCondition();
	T getTargetValue(DataItem targetItem);
	
	@Override
	default boolean validate(DataItem target) {
		T targetValue = getTargetValue(target);
		switch (getCondition()) {
			case NOT_COND:
				return true;
			case COND1_LESS_VAL:
				return getValue1().get().compareTo(targetValue) < 0 ;
				
			case COND1_LESS_EQUAL_VAL:
				return getValue1().get().compareTo(targetValue) <= 0 ;		
				
			case VAL_LESS_COND1:
				return targetValue.compareTo(getValue1().get()) < 0 ;
				
			case VAL_LESS_EQUAL_COND1:
				return targetValue.compareTo(getValue1().get()) <= 0 ;
				
			case COND1_LESS_VAL_AND_VAL_LESS_COND2:
				return (getValue1().get().compareTo(targetValue) < 0)
					&&  (targetValue.compareTo(getValue2().get()) < 0);
				
			case COND1_LESS_EQUAL_VAL_AND_VAL_LESS_EQUAL_COND2:
				return (getValue1().get().compareTo(targetValue) <= 0)
					&&  (targetValue.compareTo(getValue2().get()) <= 0);
				
			case VAL_LESS_COND1_OR_COND2_LESS_VAL:
				return (getValue1().get().compareTo(targetValue) < 0)
					  ||  (getValue2().get().compareTo(targetValue) < 0);
				
			case VAL_LESS_EQUAL_COND1_OR_COND2_LESS_EQUAL_VAL:
				return (getValue1().get().compareTo(targetValue) <= 0)
					  ||  (getValue2().get().compareTo(targetValue) <= 0);
				
			case EQUAL:
				return (getValue1().get().compareTo(targetValue) == 0);
				
			case NOT_EQUAL:
				return (getValue1().get().compareTo(targetValue) != 0);			
				
			default:
				throw new RuntimeException("実装が存在しない条件です。:" + getCondition());
		}
	}
}

