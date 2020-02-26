package nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.algorithm.dto;

import lombok.Getter;
import nts.arc.time.GeneralDate;

@Getter
public class RetirementInforDto extends EmployeeInformationImport {

	private GeneralDate dateJoinComp; // 入社日

	private GeneralDate birthday; // 誕生日
	
	private  String  pid; // 個人ID

	public RetirementInforDto(EmployeeInformationImport employeeInformationImport, GeneralDate dateJoinComp, 
			GeneralDate birthday, String pid) {
		super(employeeInformationImport.getEmployeeId(), employeeInformationImport.getEmployeeCode(), 
				employeeInformationImport.getBusinessName(), employeeInformationImport.getBusinessNameKana(), 
				employeeInformationImport.getDepartmentId(), employeeInformationImport.getDepartmentCode(), 
				employeeInformationImport.getDepartmentName(),
				employeeInformationImport.getPositionId(), employeeInformationImport.getPositionCode(), employeeInformationImport.getPositionName(), employeeInformationImport.getEmploymentCode(), employeeInformationImport.getEmploymentName());
		this.dateJoinComp = dateJoinComp;
		this.birthday = birthday;
		this.pid = pid;
	}
}
