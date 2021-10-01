package nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employmenthistory.imported;

import lombok.Builder;
import lombok.Data;

/**
 * The Class EmploymentExport.
 */
//所属雇用
@Data
@Builder
public class EmploymentImport {

	/** The employment code. */
	private String employmentCode; //雇用コード
	
	/** The employment name. */
	private String employmentName; //雇用名称
	
}