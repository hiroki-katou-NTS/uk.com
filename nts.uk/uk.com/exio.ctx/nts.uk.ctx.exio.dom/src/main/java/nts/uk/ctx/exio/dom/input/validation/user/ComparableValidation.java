package nts.uk.ctx.exio.dom.input.validation.user;


import java.util.Optional;

import nts.uk.ctx.exio.dom.input.DataItem;

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
			case COND1_LESS_TARGET:
				return getValue1().get().compareTo(targetValue) < 0 ;
				
			case COND1_LESS_EQUAL_TARGET:
				return getValue1().get().compareTo(targetValue) <= 0 ;		
				
			case TARGET_LESS_COND1:
				return targetValue.compareTo(getValue1().get()) < 0 ;
				
			case TARGET_LESS_EQUAL_COND1:
				return targetValue.compareTo(getValue1().get()) <= 0 ;
				
			case TARGET_BETWEEN_OPEN_COND:
				return (getValue1().get().compareTo(targetValue) < 0)
					&&  (targetValue.compareTo(getValue2().get()) < 0);
				
			case TARGET_BETWEEN_CLOSE_COND:
				return (getValue1().get().compareTo(targetValue) <= 0)
					&&  (targetValue.compareTo(getValue2().get()) <= 0);
				
			case TARGET_OUTSIDE_OPEN_COND:
				return (targetValue.compareTo(getValue1().get()) < 0)
					  ||  (getValue2().get().compareTo(targetValue) < 0);
				
			case TARGET_OUTSIDE_CLOSED_COND:
				return (targetValue.compareTo(getValue1().get()) <= 0)
					  ||  (getValue2().get().compareTo(targetValue) <= 0);
				
			case TARGET_EQUAL_COND1:
				return (getValue1().get().compareTo(targetValue) == 0);
				
			case TARGET_NOT_EQUAL_COND1:
				return (getValue1().get().compareTo(targetValue) != 0);			
				
			default:
				throw new RuntimeException("実装が存在しない条件です。:" + getCondition());
		}
	}
}

