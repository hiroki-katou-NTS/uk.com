package nts.uk.ctx.bs.employee.pub.workplace.affiliate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
public class AffJobTitleHistoryExport {

	/** The company id. */
	private String companyId;

	/** The employee id. */
	// 社員ID
	private String employeeId;

	/** The history items. */
	// 履歴項目
	private List<DateHistoryItemExport> historyItems;

}
