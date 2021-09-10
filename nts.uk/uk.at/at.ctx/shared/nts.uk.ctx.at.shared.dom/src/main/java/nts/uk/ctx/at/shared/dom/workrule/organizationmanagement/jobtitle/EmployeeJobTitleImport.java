package nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.jobtitle;

import lombok.Builder;
import lombok.Data;

/**
 * 社員職位
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared.就業規則.組織管理.社員情報.所属職位履歴.Imported.社員職位
 * @author lan_lt
 *
 */
@Builder
@Data
public class EmployeeJobTitleImport {
	
	/** 社員ID **/
	private String employeeId;
	
	/** 職位ID **/
	private String jobTitleId;
	
	/** 職位コード **/
	private String jobTitleCode;

}
