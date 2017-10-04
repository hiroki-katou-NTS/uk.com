package repository.person.setting.regHistory;

import java.util.Optional;

import entity.person.info.setting.regHistory.BsymtEmployeeRegistrationHistory;
import entity.person.info.setting.regHistory.BsymtEmployeeRegistrationHistoryPk;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.bs.person.dom.person.info.setting.regHistory.EmpRegHistory;
import nts.uk.ctx.bs.person.dom.person.setting.regHistory.EmpRegHistoryRepository;

public class JpaEmpRegHistoryRepository extends JpaRepository implements EmpRegHistoryRepository {

	private static final String SELECT_LAST_REG_HISTORY_QUERY_STRING = "SELECT TOP 1 er FROM BsymtEmployeeRegistrationHistory er WHERE er.registeredDate DESC";

	@Override
	public Optional<EmpRegHistory> getLastRegHistory() {
		return this.queryProxy().query(SELECT_LAST_REG_HISTORY_QUERY_STRING, BsymtEmployeeRegistrationHistory.class)
				.getSingle().map(x -> toDomain(x));
	}

	private EmpRegHistory toDomain(BsymtEmployeeRegistrationHistory entity) {

		return EmpRegHistory.createFromJavaType(entity.bsydtEmployeeRegistrationHistoryPk.registeredEmployeeID,
				entity.companyId, entity.registeredDate, entity.lastRegEmployeeID);
	}

	private BsymtEmployeeRegistrationHistory toEntity(EmpRegHistory domain) {

		return new BsymtEmployeeRegistrationHistory(
				new BsymtEmployeeRegistrationHistoryPk(domain.getRegisteredEmployeeID()), domain.getCompanyId(),
				domain.getRegisteredDate(), domain.getLastRegEmployeeID());
	}

	@Override
	public void add(EmpRegHistory domain) {
		this.commandProxy().insert(toEntity(domain));

	}

}
