package nts.uk.ctx.pr.transfer.infra.repository.emppaymentinfo;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import nts.uk.ctx.pr.transfer.dom.emppaymentinfo.EmployeeBonusPaymentMethod;
import nts.uk.ctx.pr.transfer.dom.emppaymentinfo.EmployeePaymentMethodRepository;
import nts.uk.ctx.pr.transfer.dom.emppaymentinfo.EmployeeSalaryPaymentMethod;
import nts.uk.ctx.pr.transfer.dom.emppaymentinfo.IndividualSettingAtr;

/**
 * 
 * @author HungTT
 *
 */

@Stateless
public class JpaEmpPaymentMethodRepository implements EmployeePaymentMethodRepository {

	@Override
	public List<EmployeeSalaryPaymentMethod> getSalPayMethodByHistoryId(List<String> listHistId) {
		List<EmployeeSalaryPaymentMethod> result = new ArrayList<>();
		for (String histId : listHistId) {
			result.add(new EmployeeSalaryPaymentMethod(histId, new ArrayList<>()));
		}
		return result;
	}

	@Override
	public List<EmployeeBonusPaymentMethod> getBonusPayMethodByHistoryId(List<String> listHistId) {
		List<EmployeeBonusPaymentMethod> result = new ArrayList<>();
		for (String histId : listHistId) {
			result.add(new EmployeeBonusPaymentMethod(histId, IndividualSettingAtr.PAY_WITH_SAME_SALARY_SETTING.value,
					new ArrayList<>()));
		}
		return result;
	}

}
