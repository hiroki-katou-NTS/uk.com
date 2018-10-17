package nts.uk.ctx.pr.transfer.infra.repository.emppaymentinfo;

import java.util.ArrayList;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.uk.ctx.pr.transfer.dom.emppaymentinfo.EmployeeBonusPaymentInfor;
import nts.uk.ctx.pr.transfer.dom.emppaymentinfo.EmployeePaymentInforRepository;
import nts.uk.ctx.pr.transfer.dom.emppaymentinfo.EmployeeSalaryPaymentInfor;

/**
 * 
 * @author HungTT
 *
 */

@Stateless
public class JpaEmpPaymentInforRepository implements EmployeePaymentInforRepository {

	@Override
	public Optional<EmployeeSalaryPaymentInfor> getEmpSalPaymentInfo(String employeeId) {
		return Optional.of(new EmployeeSalaryPaymentInfor(employeeId, new ArrayList<>()));
	}

	@Override
	public Optional<EmployeeBonusPaymentInfor> getEmpBonusPaymentInfo(String employeeId) {
		return Optional.of(new EmployeeBonusPaymentInfor(employeeId, new ArrayList<>()));
	}

}
