package nts.uk.ctx.at.schedule.dom.employeeinfo.employeesort;

import java.util.List;

import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.objecttype.DomainAggregate;

/**
 * 並び替え設定
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務予定.社員情報.社員並び替え
 * @author HieuLt
 *
 */

@AllArgsConstructor
public class SortSetting implements DomainAggregate {
	@Getter
	/** 会社ID **/
	private final String companyID;
	@Getter
	/** 並び替え優先順 **/
	private List<OrderedList> orderedList;

	// [C-1] 作る
	public static SortSetting getSortSet(String companyID, List<OrderedList> orderedList) {

		List<OrderedList> list = orderedList.stream().distinct().collect(Collectors.toList());
		// inv-1 @並び替え優先順.種類は重複しないこと
		if (list.size() < orderedList.size()) {
			throw new BusinessException("Msg_1612");
		}
		// inv-2 1 <= @並び替え優先順.Size <= 5
		if ((orderedList.size() == 0) || orderedList.size() >= 6) {
			throw new BusinessException("Msg_1613");
		}
		return new SortSetting(companyID, orderedList);
	}

}
