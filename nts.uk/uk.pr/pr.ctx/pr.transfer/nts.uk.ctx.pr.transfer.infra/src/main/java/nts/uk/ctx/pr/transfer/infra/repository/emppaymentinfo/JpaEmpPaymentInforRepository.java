package nts.uk.ctx.pr.transfer.infra.repository.emppaymentinfo;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.transfer.dom.emppaymentinfo.EmployeeBonusPaymentInfor;
import nts.uk.ctx.pr.transfer.dom.emppaymentinfo.EmployeePaymentInforRepository;
import nts.uk.ctx.pr.transfer.dom.emppaymentinfo.EmployeeSalaryPaymentInfor;
import nts.uk.ctx.pr.transfer.infra.entity.emppaymentinfo.QxxmtEmpBonPayInfo;
import nts.uk.ctx.pr.transfer.infra.entity.emppaymentinfo.QxxmtEmpSalPayInfo;

/**
 * 
 * @author HungTT
 *
 */

@Stateless
public class JpaEmpPaymentInforRepository extends JpaRepository implements EmployeePaymentInforRepository {

	@Override
	public Optional<EmployeeSalaryPaymentInfor> getEmpSalPaymentInfo(String employeeId) {
		String query = "SELECT e FROM QxxmtEmpSalPayInfo e WHERE e.pk.employeeId = :employeeId";
		List<QxxmtEmpSalPayInfo> listEntity = this.queryProxy().query(query, QxxmtEmpSalPayInfo.class)
				.setParameter("employeeId", employeeId).getList();
		return listEntity.isEmpty() ? Optional.empty() : Optional.of(QxxmtEmpSalPayInfo.toDomain(listEntity));
	}

	@Override
	public Optional<EmployeeBonusPaymentInfor> getEmpBonusPaymentInfo(String employeeId) {
		String query = "SELECT e FROM QxxmtEmpBonPayInfo e WHERE e.pk.employeeId = :employeeId";
		List<QxxmtEmpBonPayInfo> listEntity = this.queryProxy().query(query, QxxmtEmpBonPayInfo.class)
				.setParameter("employeeId", employeeId).getList();
		return listEntity.isEmpty() ? Optional.empty() : Optional.of(QxxmtEmpBonPayInfo.toDomain(listEntity));
	}

}
