package nts.uk.ctx.bs.person.dom.person.info.category;

import lombok.AllArgsConstructor;
import nts.arc.primitive.constraint.IntegerRange;

@AllArgsConstructor
@IntegerRange(max = 5, min = 1)
public enum CategoryType {
	// 1:単一情報(SingleInfo)
	SINGLEINFO(1),
	// 2:複数情報(MultiInfo)
	MULTIINFO(2),
	// 3:連続履歴(ContinuousHistory)
	CONTINUOUSHISTORY(3),
	// 4:非連続履歴(NoDuplicateHistory)
	NODUPLICATEHISTORY(4),
	// 5:重複履歴(DuplicateHistory)
	DUPLICATEHISTORY(5),
	//6: 連続履歴（期間指定）
	CONTINUOUS_HISTORY(6);
	
	public final int value;
}
