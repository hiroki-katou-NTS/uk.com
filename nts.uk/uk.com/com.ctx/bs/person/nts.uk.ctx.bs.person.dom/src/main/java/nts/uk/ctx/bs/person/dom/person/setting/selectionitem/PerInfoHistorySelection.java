package nts.uk.ctx.bs.person.dom.person.setting.selectionitem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@AllArgsConstructor
@Getter
@Setter

/**
 * 
 * @author tuannv
 *
 */

// 選択肢履歴ドメイン
public class PerInfoHistorySelection {
	private String histId;
	private String selectionItemId;
	private String companyCode;
	private DatePeriod period;
	// private GeneralDate startDate;
	// private GeneralDate endDate;

	public static PerInfoHistorySelection createHistorySelection(String histId, String selectionItemId,
			String companyCode, DatePeriod period) {

		// 選択肢履歴 パラメーター帰還
		return new PerInfoHistorySelection(histId, selectionItemId, companyCode, period);
	}
}
