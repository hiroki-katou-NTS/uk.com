package nts.uk.ctx.pr.core.app.find.rule.employment.allot;

import lombok.Value;
import nts.uk.ctx.pr.core.dom.rule.employment.layout.allot.getEmployeeList;
@Value
public class getEmploymentListDto {
	String companyCode;
	String historyId;
	String employeeCode;
	String employeeName;
	String paymentDetailCode;
	String paymentDatailName;
	String bonusDetailCode;
	String bonusDetailName;
	
	public static getEmploymentListDto fromDomain(getEmployeeList getEmployeeList){
		return new getEmploymentListDto(getEmployeeList.getCompanyCode().v(),
				getEmployeeList.getHistoryId(),
				getEmployeeList.getEmployeeCode().v(),
				getEmployeeList.getEmployeeName().v(),
				getEmployeeList.getPaymentDetailCode().v(),
				getEmployeeList.getPaymentDetailName().v(),
				getEmployeeList.getBonusDetailCode().v(),
				getEmployeeList.getEmployeeName().v());
		
	}
	
}
