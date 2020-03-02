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
import nts.uk.ctx.pr.transfer.infra.entity.emppaymentinfo.QbtmtEmpBonPayMethod;
import nts.uk.ctx.pr.transfer.infra.entity.emppaymentinfo.QbtmtEmpSalPayMethod;

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
		String query = "SELECT e FROM QbtmtEmpSalPayMethod e WHERE e.pk.historyId IN :listHistId";
		Map<String, List<QbtmtEmpSalPayMethod>> mapEntity = this.queryProxy().query(query, QbtmtEmpSalPayMethod.class)
				.setParameter("listHistId", listHistId).getList().stream()
				.collect(Collectors.groupingBy(m -> m.pk.historyId));
		List<EmployeeSalaryPaymentMethod> result = new ArrayList<>();
		mapEntity.entrySet().forEach(e -> {
			List<QbtmtEmpSalPayMethod> listEntity = e.getValue();
			result.add(QbtmtEmpSalPayMethod.toDomain(listEntity));
		});
		return result;
	}

	@Override
	public List<EmployeeBonusPaymentMethod> getBonusPayMethodByHistoryId(List<String> listHistId) {
		if (listHistId.isEmpty())
			return Collections.emptyList();
		String query = "SELECT e FROM QbtmtEmpBonPayMethod e WHERE e.pk.historyId IN :listHistId";
		Map<String, List<QbtmtEmpBonPayMethod>> mapEntity = this.queryProxy().query(query, QbtmtEmpBonPayMethod.class)
				.setParameter("listHistId", listHistId).getList().stream()
				.collect(Collectors.groupingBy(m -> m.pk.historyId));
		List<EmployeeBonusPaymentMethod> result = new ArrayList<>();
		mapEntity.entrySet().forEach(e -> {
			List<QbtmtEmpBonPayMethod> listEntity = e.getValue();
			result.add(QbtmtEmpBonPayMethod.toDomain(listEntity));
		});
		return result;
	}

	@Override
	public Optional<EmployeeSalaryPaymentMethod> getSalPayMethodByHistoryId(String histId) {
		String query = "SELECT e FROM QbtmtEmpSalPayMethod e WHERE e.pk.historyId = :histId";
		List<QbtmtEmpSalPayMethod> listEntity = this.queryProxy().query(query, QbtmtEmpSalPayMethod.class)
				.setParameter("histId", histId).getList();
		return listEntity.isEmpty() ? Optional.empty() : Optional.of(QbtmtEmpSalPayMethod.toDomain(listEntity));
	}

	@Override
	public Optional<EmployeeBonusPaymentMethod> getBonusPayMethodByHistoryId(String histId) {
		String query = "SELECT e FROM QbtmtEmpBonPayMethod e WHERE e.pk.historyId = :histId";
		List<QbtmtEmpBonPayMethod> listEntity = this.queryProxy().query(query, QbtmtEmpBonPayMethod.class)
				.setParameter("histId", histId).getList();
		return listEntity.isEmpty() ? Optional.empty() : Optional.of(QbtmtEmpBonPayMethod.toDomain(listEntity));
	}

	@Override
	public void addEmpSalaryPayMethod(EmployeeSalaryPaymentMethod domain) {
		this.commandProxy().insertAll(QbtmtEmpSalPayMethod.fromDomain(domain));
	}

	@Override
	public void addEmpBonusPayMethod(EmployeeBonusPaymentMethod domain) {
		this.commandProxy().insertAll(QbtmtEmpBonPayMethod.fromDomain(domain));
	}

	@Override
	public void updateEmpSalaryPayMethod(EmployeeSalaryPaymentMethod domain) {
		String query = "SELECT e FROM QbtmtEmpSalPayMethod e WHERE e.pk.historyId = :histId";
		List<QbtmtEmpSalPayMethod> listEntity = this.queryProxy().query(query, QbtmtEmpSalPayMethod.class)
				.setParameter("histId", domain.getHistoryId()).getList();
		this.commandProxy().removeAll(listEntity);
		this.getEntityManager().flush();
		this.commandProxy().insertAll(QbtmtEmpSalPayMethod.fromDomain(domain));
	}

	@Override
	public void updateEmpBonusPayMethod(EmployeeBonusPaymentMethod domain) {
		String query = "SELECT e FROM QbtmtEmpBonPayMethod e WHERE e.pk.historyId = :histId";
		List<QbtmtEmpBonPayMethod> listEntity = this.queryProxy().query(query, QbtmtEmpBonPayMethod.class)
				.setParameter("histId", domain.getHistoryId()).getList();
		this.commandProxy().removeAll(listEntity);
		this.getEntityManager().flush();
		this.commandProxy().insertAll(QbtmtEmpBonPayMethod.fromDomain(domain));
	}

}
