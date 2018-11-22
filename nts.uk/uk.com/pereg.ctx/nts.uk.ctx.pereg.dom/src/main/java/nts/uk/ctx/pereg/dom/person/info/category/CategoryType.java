package nts.uk.ctx.pereg.dom.person.info.category;

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
	 
	//	連続履歴（期間指定）：６
	//	→雇用契約履歴の場合は、契約満了日（終了日）ができる必要があるため、この区分を設けた。
	//	※他の連続履歴は開始日だけ変更可
	// Tạm dịch như dưới
//	Continuous history (period designation): 6
//	→ In the case of employment contract history, it is necessary to have the contract expiration date (end date)
//	This category was established.
//	※ Other continuous history can be changed only on the start date , lien quan den CS00021-勤務種別
	CONTINUOUS_HISTORY_FOR_ENDDATE(6);
	
	public final int value;
}
