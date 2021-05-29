package nts.uk.ctx.exio.dom.exi.condset;

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
//		case TARGET_LARGER_THAN_VALUE1:
//			return targetValue.compareTo(getValue1()) > 0;
		default:
			throw new RuntimeException();
		}
	}
}
