package nts.uk.ctx.hr.shared.dom.employee;

import java.util.List;

import lombok.Data;
/**
 * 
 * @author yennth
 *
 */
@Data
public class GrpCmonMasterImport {
	// 共通マスタ名
	private String commonMasterName;
	
	// 共通マスタ項目
	private List<GrpCmmMastItImport> commonMasterItems;

	public GrpCmonMasterImport(String commonMasterName, List<GrpCmmMastItImport> commonMasterItems) {
		super();
		this.commonMasterName = commonMasterName;
		this.commonMasterItems = commonMasterItems;
	}
}
