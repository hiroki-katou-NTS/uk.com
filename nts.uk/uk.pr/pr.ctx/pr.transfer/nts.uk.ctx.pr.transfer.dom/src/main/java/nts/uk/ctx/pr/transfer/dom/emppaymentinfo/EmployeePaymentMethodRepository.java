package nts.uk.ctx.pr.transfer.dom.emppaymentinfo;

import java.util.List;

/**
 * 
 * @author HungTT
 *
 */

public interface EmployeePaymentMethodRepository {

	public List<EmployeeSalaryPaymentMethod> getSalPayMethodByHistoryId(List<String> listHistId);
	
	public List<EmployeeBonusPaymentMethod> getBonusPayMethodByHistoryId(List<String> listHistId);
	
}
