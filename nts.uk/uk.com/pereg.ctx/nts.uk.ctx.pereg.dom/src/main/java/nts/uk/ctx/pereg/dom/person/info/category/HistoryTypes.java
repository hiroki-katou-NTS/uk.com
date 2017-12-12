package nts.uk.ctx.pereg.dom.person.info.category;

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
	DUPLICATE(3),
	
	// 4: 連続（期間指定）
	CONTINUOUS_HISTORY(4);
	
	public final int value;
}
