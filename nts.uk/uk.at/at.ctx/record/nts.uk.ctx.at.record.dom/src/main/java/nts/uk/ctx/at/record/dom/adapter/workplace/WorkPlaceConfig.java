package nts.uk.ctx.at.record.dom.adapter.workplace;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
// 職場構成
public class WorkPlaceConfig {
	/** The company id. */
	// 会社ID
	private String companyId;

	/** The wkp config history. */
	// 履歴
	private List<WorkplaceConfigHistory> wkpConfigHistory;
}