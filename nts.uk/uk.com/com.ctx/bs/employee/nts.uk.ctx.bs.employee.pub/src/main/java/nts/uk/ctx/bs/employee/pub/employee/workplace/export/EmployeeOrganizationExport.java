package nts.uk.ctx.bs.employee.pub.employee.workplace.export;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EmployeeOrganizationExport {
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
	
	
	
	
}
