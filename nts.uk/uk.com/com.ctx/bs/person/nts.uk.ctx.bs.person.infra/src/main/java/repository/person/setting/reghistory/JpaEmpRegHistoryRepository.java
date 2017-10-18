package repository.person.setting.reghistory;

import java.util.Optional;

import javax.ejb.Stateless;

import entity.person.info.setting.reghistory.PpedtEmployeeRegistrationHistory;
import entity.person.info.setting.reghistory.PpedtEmployeeRegistrationHistoryPk;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.bs.person.dom.person.info.setting.reghistory.EmpRegHistory;
import nts.uk.ctx.bs.person.dom.person.setting.reghistory.EmpRegHistoryRepository;

@Stateless
public class JpaEmpRegHistoryRepository extends JpaRepository implements EmpRegHistoryRepository {

	private static final String SELECT_LAST_REG_HISTORY_QUERY_STRING = "SELECT  er FROM PpedtEmployeeRegistrationHistory er ORDER BY er.registeredDate DESC";

	@Override
	public Optional<EmpRegHistory> getLastRegHistory() {

		return this.queryProxy().query(SELECT_LAST_REG_HISTORY_QUERY_STRING, PpedtEmployeeRegistrationHistory.class)
				.getSingle().map(x -> toDomain(x));
	}

	private EmpRegHistory toDomain(PpedtEmployeeRegistrationHistory entity) {

		return EmpRegHistory.createFromJavaType(entity.ppedtEmployeeRegistrationHistoryPk.registeredEmployeeID,
				entity.companyId, entity.registeredDate, entity.lastRegEmployeeID);
	}

	private PpedtEmployeeRegistrationHistory toEntity(EmpRegHistory domain) {

		return new PpedtEmployeeRegistrationHistory(
				new PpedtEmployeeRegistrationHistoryPk(domain.getRegisteredEmployeeID()), domain.getCompanyId(),
				domain.getRegisteredDate(), domain.getLastRegEmployeeID());
	}

	@Override
	public void add(EmpRegHistory domain) {
		this.commandProxy().insert(toEntity(domain));

	}

}
