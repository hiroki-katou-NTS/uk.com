package nts.uk.ctx.bs.employee.dom.workplace.affiliate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.bs.employee.dom.workplace.info.WorkplaceCode;

/**
 * The Class AffWorkplaceHistoryItem.
 */
// 所属職場履歴項目
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AffWorkplaceHistoryItem {

	/** The history Id. */
	// 履歴ID
	private String historyId;
	
	/** The Employee Id. */
	// 社員ID
	private String employeeId;
	
	/** The workplaceCode. */
	// 職場コード
	private String  workplaceId;
	
	/** The normalWorkplaceCode. */
	// 通常職場コード
	private String  normalWorkplaceId;
	
	/** The normalWorkplaceCode. */
	// 場所コード
	private LocationCode  locationCode;

	public static AffWorkplaceHistoryItem createFromJavaType(String histId, String employeeId, String workplaceId, String normalWorkplaceId, String locationCode){
		return new AffWorkplaceHistoryItem(histId,employeeId, workplaceId, normalWorkplaceId, new LocationCode(locationCode));

	}
	
}
