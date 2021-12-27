package nts.uk.ctx.at.record.dom.stampmanagement.setting.preparation.smartphonestamping.employee.adapter;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AffWorkplaceHistoryItemImport {

	public AffWorkplaceHistoryItemImport() {
	}

	/** The history Id. */
	// 履歴ID
	private String historyId;
	/** The workplaceId. */
	// 職場ID
	private String workplaceId;

	/** The normalWorkplaceCode. */
	// 通常職場コード
	private String normalWorkplaceId;

}
