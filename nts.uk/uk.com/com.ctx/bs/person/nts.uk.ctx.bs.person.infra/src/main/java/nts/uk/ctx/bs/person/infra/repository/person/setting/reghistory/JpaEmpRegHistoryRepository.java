package nts.uk.ctx.bs.person.infra.repository.person.setting.reghistory;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.bs.person.dom.person.info.setting.reghistory.EmpRegHistory;
import nts.uk.ctx.bs.person.dom.person.setting.reghistory.EmpRegHistoryRepository;
import nts.uk.ctx.bs.person.infra.entity.person.info.setting.reghistory.PpedtEmployeeRegistrationHistory;
import nts.uk.ctx.bs.person.infra.entity.person.info.setting.reghistory.PpedtEmployeeRegistrationHistoryPk;

@Stateless
public class JpaEmpRegHistoryRepository extends JpaRepository implements EmpRegHistoryRepository {

	@Override
	public Optional<EmpRegHistory> getLastRegHistory(String registeredEmployeeID) {
		return this.queryProxy().find(new PpedtEmployeeRegistrationHistoryPk(registeredEmployeeID),
				PpedtEmployeeRegistrationHistory.class).map(x -> toDomain(x));
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

	@Override
	public void update(EmpRegHistory newEmpRegHistory) {

		Optional<PpedtEmployeeRegistrationHistory> optRegHist = this.queryProxy().find(
				new PpedtEmployeeRegistrationHistoryPk(newEmpRegHistory.getRegisteredEmployeeID()),
				PpedtEmployeeRegistrationHistory.class);

		if (optRegHist.isPresent()) {
			this.commandProxy().update(optRegHist.get().updateFromDomain(newEmpRegHistory));
		}

	}

}
