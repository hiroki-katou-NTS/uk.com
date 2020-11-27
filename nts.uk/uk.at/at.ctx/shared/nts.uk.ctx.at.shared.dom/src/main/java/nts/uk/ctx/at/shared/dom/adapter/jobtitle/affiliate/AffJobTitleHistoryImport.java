package nts.uk.ctx.at.shared.dom.adapter.jobtitle.affiliate;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class AffJobTitleHistoryImport {

	/** The company id. */
	private String companyId;

	/** The employee id. */
	// 社員ID
	private String employeeId;

	/** The history items. */
	// 履歴項目
	private List<DateHistoryItemImport> historyItems;

}
