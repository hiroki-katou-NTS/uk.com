package nts.uk.shr.com.history.strategic;

import java.util.Arrays;
import java.util.List;

import nts.gul.util.range.ComparableRange;
import nts.uk.shr.com.history.constraint.HistoryConstraint;
import nts.uk.shr.com.history.constraint.MustBeResident;

public interface ContinuousResidentHistory<S extends ComparableRange<S, D>, D extends Comparable<D>>
		extends ContinuousHistory<S, D> {

	@Override
	default List<HistoryConstraint<S, D>> constraints() {
		return Arrays.asList(new MustBeResident<>());
	}
}
