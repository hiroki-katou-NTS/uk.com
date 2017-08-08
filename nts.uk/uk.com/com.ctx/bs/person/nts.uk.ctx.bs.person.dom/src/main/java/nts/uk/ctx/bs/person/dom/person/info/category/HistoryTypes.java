package nts.uk.ctx.bs.person.dom.person.info.category;

import lombok.AllArgsConstructor;
import nts.arc.primitive.constraint.IntegerRange;

@AllArgsConstructor
@IntegerRange(max = 3, min = 1)
public enum HistoryTypes {
	
	// 1:連続(Continuous)
	CONTINUOUS(1),
	
	// 2:非連続(NoDuplicate)
	NO_DUPLICATE(2),
	
	// 3: 重複 (Duplicate)
	DUPLICATE(3);

	public final int value;
}
