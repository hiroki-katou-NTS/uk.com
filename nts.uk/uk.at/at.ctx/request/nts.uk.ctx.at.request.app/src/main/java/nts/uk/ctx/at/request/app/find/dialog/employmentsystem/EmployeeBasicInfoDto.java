package nts.uk.ctx.at.request.app.find.dialog.employmentsystem;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.PersonEmpBasicInfoImport;

@AllArgsConstructor
@Value
public class EmployeeBasicInfoDto {
	//個人ID
	public String personId;
	
	//社員ID
	public String employeeId;
	
	//ビジネスネーム
	public String businessName;
	
	//性別
	public int gender;
	
	//生年月日
	public GeneralDate birthday;
	
	//社員コード
	public String employeeCode;
	
	//入社年月日
	public GeneralDate jobEntryDate;
	
	//退職年月日
	public GeneralDate retirementDate;
	
	/**
	 * fromDomain
	 * 
	 * @param domain
	 * @return
	 */
	public static EmployeeBasicInfoDto fromDomain(PersonEmpBasicInfoImport domain) {
		return new EmployeeBasicInfoDto(domain.getPersonId(), domain.getEmployeeId(), domain.getBusinessName(), domain.getGender(),
				domain.getBirthday(), domain.getEmployeeCode(), domain.getJobEntryDate(),
				domain.getRetirementDate());
	}
}
