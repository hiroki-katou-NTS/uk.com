package nts.uk.ctx.hr.shared.dom.employee;

import lombok.Data;

/**
 * 
 * @author yennth
 *
 */
@Data
public class GrpCmmMastItImport {
	// 共通項目ID
	private String commonMasterItemId;
	
	// 共通項目名
	private String commonMasterItemName;
	
	// 表示順
	private int displayNumber;

	public GrpCmmMastItImport(String commonMasterItemId, String commonMasterItemName, int displayNumber) {
		super();
		this.commonMasterItemId = commonMasterItemId;
		this.commonMasterItemName = commonMasterItemName;
		this.displayNumber = displayNumber;
	}
}
