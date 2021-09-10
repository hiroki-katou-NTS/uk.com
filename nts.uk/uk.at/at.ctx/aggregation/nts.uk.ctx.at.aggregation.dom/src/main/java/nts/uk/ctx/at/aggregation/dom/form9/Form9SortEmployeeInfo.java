package nts.uk.ctx.at.aggregation.dom.form9;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.LicenseClassification;
@Setter
@Getter
@AllArgsConstructor
public class Form9SortEmployeeInfo {
	
	/** 社員ID **/
	private final String employeeId;
	
	/** 免許区分 **/
	private final LicenseClassification licenseClassification;
	
	/** 職位コード **/
	private final String jobTitleCode;
	
	/** 社員コード **/
	private final String employeeCode;
	
}
