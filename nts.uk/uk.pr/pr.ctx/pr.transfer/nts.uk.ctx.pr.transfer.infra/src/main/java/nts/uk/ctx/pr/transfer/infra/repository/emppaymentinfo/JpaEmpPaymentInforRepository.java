package nts.uk.ctx.pr.transfer.infra.repository.emppaymentinfo;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.transfer.dom.emppaymentinfo.EmployeeBonusPaymentInfor;
import nts.uk.ctx.pr.transfer.dom.emppaymentinfo.EmployeePaymentInforRepository;
import nts.uk.ctx.pr.transfer.dom.emppaymentinfo.EmployeeSalaryPaymentInfor;
import nts.uk.ctx.pr.transfer.infra.entity.emppaymentinfo.QbtmtEmpBonPayInfo;
import nts.uk.ctx.pr.transfer.infra.entity.emppaymentinfo.QbtmtEmpSalPayInfo;

/**
 * 
 * @author HungTT
 *
 */

@Stateless
public class JpaEmpPaymentInforRepository extends JpaRepository implements EmployeePaymentInforRepository {

	@Override
	public Optional<EmployeeSalaryPaymentInfor> getEmpSalPaymentInfo(String employeeId) {
		String query = "SELECT e FROM QbtmtEmpSalPayInfo e WHERE e.pk.employeeId = :employeeId";
		List<QbtmtEmpSalPayInfo> listEntity = this.queryProxy().query(query, QbtmtEmpSalPayInfo.class)
				.setParameter("employeeId", employeeId).getList();
		return listEntity.isEmpty() ? Optional.empty() : Optional.of(QbtmtEmpSalPayInfo.toDomain(listEntity));
	}

	@Override
	public Optional<EmployeeBonusPaymentInfor> getEmpBonusPaymentInfo(String employeeId) {
		String query = "SELECT e FROM QbtmtEmpBonPayInfo e WHERE e.pk.employeeId = :employeeId";
		List<QbtmtEmpBonPayInfo> listEntity = this.queryProxy().query(query, QbtmtEmpBonPayInfo.class)
				.setParameter("employeeId", employeeId).getList();
		return listEntity.isEmpty() ? Optional.empty() : Optional.of(QbtmtEmpBonPayInfo.toDomain(listEntity));
	}

}
