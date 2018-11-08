package nts.uk.ctx.pr.transfer.dom.emppaymentinfo;

import java.util.List;
import java.util.Optional;

/**
 * 
 * @author HungTT
 *
 */

public interface EmployeePaymentMethodRepository {

	public List<EmployeeSalaryPaymentMethod> getSalPayMethodByHistoryId(List<String> listHistId);
	
	public List<EmployeeBonusPaymentMethod> getBonusPayMethodByHistoryId(List<String> listHistId);
	
	public Optional<EmployeeSalaryPaymentMethod> getSalPayMethodByHistoryId(String histId);
	
	public Optional<EmployeeBonusPaymentMethod> getBonusPayMethodByHistoryId(String histId);
	
	public void addEmpSalaryPayMethod(EmployeeSalaryPaymentMethod domain);
	
	public void addEmpBonusPayMethod(EmployeeBonusPaymentMethod domain);
	
}
