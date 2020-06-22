package nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.adapter;

import java.util.Optional;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EmployeeOrganizationImport {

	/** ビジネスネーム **/
	private Optional<String> businessName;
	/** 社員ID **/
	private String employeeId;
	/** 社員コード **/
	private Optional<String> employeeCd;
	/**職場ID **/ 
	private String workplaceId;
	/**職場グループID **/
	private Optional<String> workplaceGroupId;
	public EmployeeOrganizationImport(Optional<String> businessName, String employeeId, Optional<String> employeeCd,
			String workplaceId, Optional<String> workplaceGroupId) {
		super();
		this.businessName = businessName;
		this.employeeId = employeeId;
		this.employeeCd = employeeCd;
		this.workplaceId = workplaceId;
		this.workplaceGroupId = workplaceGroupId;
	}
	
}
