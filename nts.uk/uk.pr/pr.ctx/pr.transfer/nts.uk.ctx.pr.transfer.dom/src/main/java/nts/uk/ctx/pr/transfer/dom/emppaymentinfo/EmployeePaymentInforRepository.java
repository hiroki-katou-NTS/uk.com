package nts.uk.ctx.pr.transfer.dom.emppaymentinfo;

import java.util.Optional;

/**
 * 
 * @author HungTT
 *
 */

public interface EmployeePaymentInforRepository {
	
	public Optional<EmployeeSalaryPaymentInfor> getEmpSalPaymentInfo(String employeeId);
	
	public Optional<EmployeeBonusPaymentInfor> getEmpBonusPaymentInfo(String employeeId);

}
