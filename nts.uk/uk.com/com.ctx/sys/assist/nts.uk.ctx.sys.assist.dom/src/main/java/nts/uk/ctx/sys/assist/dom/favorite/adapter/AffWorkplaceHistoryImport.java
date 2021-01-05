package nts.uk.ctx.sys.assist.dom.favorite.adapter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AffWorkplaceHistoryImport {
	
	// 社員ID
	private String employeeId;

	/** The workplace id. */
	// 職場ID
	private String workplaceId;
	
	// 履歴ID
	private String historyID;
	
	//通常職場ID
	private String normalWorkplaceID;

}