package nts.uk.ctx.at.shared.dom.remainingnumber.paymana;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * The Class SWkpHistExport.
 * 所属職場履歴項目
 *
 */
@Data
@AllArgsConstructor
public class AffWorkplaceHistoryItemImport {
	/** The history Id. */
	// 履歴ID
	private String historyId;
	/** The workplaceId. */
	// 職場ID
	private String  workplaceId;
	
	/** The normalWorkplaceCode. */
	// 通常職場コード
	private String  normalWorkplaceId;
}
