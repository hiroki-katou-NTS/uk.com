package nts.uk.ctx.bs.person.dom.person.setting.selectionitem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;

@AllArgsConstructor
@Getter
public class PerInfoHistorySelection {
	private String histId;
	private String selectionItemId;
	private String companyCode;
	private GeneralDate startDate;
	private GeneralDate endDate;

	public static PerInfoHistorySelection historySelection(String histId, String selectionItemId, String companyCode,
			GeneralDate endDate, GeneralDate startDate) {
		return new PerInfoHistorySelection(histId, selectionItemId, companyCode, startDate, endDate);
	}
}
