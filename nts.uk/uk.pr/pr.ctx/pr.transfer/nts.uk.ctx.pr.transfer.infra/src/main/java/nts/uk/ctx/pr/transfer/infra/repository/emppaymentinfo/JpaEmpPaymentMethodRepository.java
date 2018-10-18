package nts.uk.ctx.pr.transfer.infra.repository.emppaymentinfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.transfer.dom.emppaymentinfo.EmployeeBonusPaymentMethod;
import nts.uk.ctx.pr.transfer.dom.emppaymentinfo.EmployeePaymentMethodRepository;
import nts.uk.ctx.pr.transfer.dom.emppaymentinfo.EmployeeSalaryPaymentMethod;
import nts.uk.ctx.pr.transfer.infra.entity.emppaymentinfo.QxxmtEmpBonPayMethod;
import nts.uk.ctx.pr.transfer.infra.entity.emppaymentinfo.QxxmtEmpSalPayMethod;

/**
 * 
 * @author HungTT
 *
 */

@Stateless
public class JpaEmpPaymentMethodRepository extends JpaRepository implements EmployeePaymentMethodRepository {

	@Override
	public List<EmployeeSalaryPaymentMethod> getSalPayMethodByHistoryId(List<String> listHistId) {
		if (listHistId.isEmpty())
			return Collections.emptyList();
		String query = "SELECT e FROM QxxmtEmpSalPayMethod e WHERE e.pk.historyId IN :listHistId";
		Map<String, List<QxxmtEmpSalPayMethod>> mapEntity = this.queryProxy().query(query, QxxmtEmpSalPayMethod.class)
				.setParameter("listHistId", listHistId).getList().stream()
				.collect(Collectors.groupingBy(m -> m.pk.historyId));
		List<EmployeeSalaryPaymentMethod> result = new ArrayList<>();
		mapEntity.entrySet().forEach(e -> {
			List<QxxmtEmpSalPayMethod> listEntity = e.getValue();
			result.add(QxxmtEmpSalPayMethod.toDomain(listEntity));
		});
		return result;
	}

	@Override
	public List<EmployeeBonusPaymentMethod> getBonusPayMethodByHistoryId(List<String> listHistId) {
		if (listHistId.isEmpty())
			return Collections.emptyList();
		String query = "SELECT e FROM QxxmtEmpBonPayMethod e WHERE e.pk.historyId IN :listHistId";
		Map<String, List<QxxmtEmpBonPayMethod>> mapEntity = this.queryProxy().query(query, QxxmtEmpBonPayMethod.class)
				.setParameter("listHistId", listHistId).getList().stream()
				.collect(Collectors.groupingBy(m -> m.pk.historyId));
		List<EmployeeBonusPaymentMethod> result = new ArrayList<>();
		mapEntity.entrySet().forEach(e -> {
			List<QxxmtEmpBonPayMethod> listEntity = e.getValue();
			result.add(QxxmtEmpBonPayMethod.toDomain(listEntity));
		});
		return result;
	}

	@Override
	public Optional<EmployeeSalaryPaymentMethod> getSalPayMethodByHistoryId(String histId) {
		String query = "SELECT e FROM QxxmtEmpSalPayMethod e WHERE e.pk.historyId = :histId";
		List<QxxmtEmpSalPayMethod> listEntity = this.queryProxy().query(query, QxxmtEmpSalPayMethod.class)
				.setParameter("histId", histId).getList();
		return listEntity.isEmpty() ? Optional.empty() : Optional.of(QxxmtEmpSalPayMethod.toDomain(listEntity));
	}

	@Override
	public Optional<EmployeeBonusPaymentMethod> getBonusPayMethodByHistoryId(String histId) {
		String query = "SELECT e FROM QxxmtEmpBonPayMethod e WHERE e.pk.historyId = :histId";
		List<QxxmtEmpBonPayMethod> listEntity = this.queryProxy().query(query, QxxmtEmpBonPayMethod.class)
				.setParameter("histId", histId).getList();
		return listEntity.isEmpty() ? Optional.empty() : Optional.of(QxxmtEmpBonPayMethod.toDomain(listEntity));
	}

	@Override
	public void addEmpSalaryPayMethod(EmployeeSalaryPaymentMethod domain) {
		this.commandProxy().insertAll(QxxmtEmpSalPayMethod.fromDomain(domain));
	}

	@Override
	public void addEmpBonusPayMethod(EmployeeBonusPaymentMethod domain) {
		this.commandProxy().insertAll(QxxmtEmpBonPayMethod.fromDomain(domain));
	}

}
