package nts.uk.ctx.at.shared.dom.adapter.employee;

import lombok.Builder;
import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmployeeImport.EmployeeImportBuilder;

@Getter
@Builder
public class PersonEmpBasicInfoImport {
	// 個人ID
	private String personId;

	// 社員ID
	private String employeeId;

	// ビジネスネーム
	private String businessName;

	// 性別
	private int gender;

	// 生年月日
	private GeneralDate birthday;

	// 社員コード
	private String employeeCode;

	// 入社年月日
	private GeneralDate jobEntryDate;

	// 退職年月日
	private GeneralDate retirementDate;

	public PersonEmpBasicInfoImport(String personId, String employeeId, String businessName, int gender, GeneralDate birthday, String employeeCode, GeneralDate jobEntryDate, GeneralDate retirementDate) {
		super();
		this.personId = personId;
		this.employeeId = employeeId;
		this.businessName = businessName;
		this.gender = gender;
		this.birthday = birthday;
		this.employeeCode = employeeCode;
		this.jobEntryDate = jobEntryDate;
		this.retirementDate = retirementDate;
	}

	
	
}
