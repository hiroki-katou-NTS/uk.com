package nts.uk.ctx.pereg.infra.repository.reghistory;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pereg.dom.reghistory.EmpRegHistory;
import nts.uk.ctx.pereg.dom.reghistory.EmpRegHistoryRepository;
import nts.uk.ctx.pereg.dom.reghistory.LastEmRegHistory;
import nts.uk.ctx.pereg.infra.entity.reghistory.PpedtEmployeeRegistrationHistory;
import nts.uk.ctx.pereg.infra.entity.reghistory.PpedtEmployeeRegistrationHistoryPk;

@Stateless
public class JpaEmpRegHistoryRepository extends JpaRepository implements EmpRegHistoryRepository {

	private static final String SELECT_ALL = "SELECT rh FROM PpedtEmployeeRegistrationHistory rh";

	private static final String GET_LAST_REG_BY_COMPANY_QUERY_STRING = SELECT_ALL
			+ " WHERE rh.companyId= :companyId  ORDER BY rh.registeredDate ";

	@Override
	public Optional<LastEmRegHistory> getLastRegHistory(String registeredEmployeeID, String companyId) {

		Optional<PpedtEmployeeRegistrationHistory> optEmpHist = this.queryProxy().find(
				new PpedtEmployeeRegistrationHistoryPk(registeredEmployeeID), PpedtEmployeeRegistrationHistory.class);

		Optional<PpedtEmployeeRegistrationHistory> optCompHist = this.getEntityManager()
				.createQuery(GET_LAST_REG_BY_COMPANY_QUERY_STRING, PpedtEmployeeRegistrationHistory.class)
				.setParameter("companyId", companyId).setMaxResults(1).getResultList().stream().findFirst();

		LastEmRegHistory result = toLastDomain(optEmpHist, optCompHist);
		return Optional
				.ofNullable(result.getLastRegEmployeeID() == null && result.getLastRegEmployeeOfCompanyID() == null
						? null : result);

	}

	private EmpRegHistory toDomain(PpedtEmployeeRegistrationHistory entity) {

		return EmpRegHistory.createFromJavaType(entity.ppedtEmployeeRegistrationHistoryPk.registeredEmployeeID,
				entity.companyId, entity.registeredDate, entity.lastRegEmployeeID);
	}

	private LastEmRegHistory toLastDomain(Optional<PpedtEmployeeRegistrationHistory> optEmpHist,
			Optional<PpedtEmployeeRegistrationHistory> optCompHist) {

		LastEmRegHistory result = new LastEmRegHistory();
		if (optEmpHist.isPresent()) {
			PpedtEmployeeRegistrationHistory empHist = optEmpHist.get();

			result.setCompanyId(empHist.companyId);
			result.setLastRegEmployeeID(empHist.lastRegEmployeeID);
			result.setRegisteredDate(empHist.registeredDate);
			result.setRegisteredEmployeeID(empHist.ppedtEmployeeRegistrationHistoryPk.registeredEmployeeID);

		}

		if (optCompHist.isPresent()) {
			if (!optCompHist.get().lastRegEmployeeID.equals(result.getLastRegEmployeeID())) {
				PpedtEmployeeRegistrationHistory comHist = optCompHist.get();
				result.setLastRegEmployeeOfCompanyID(comHist.lastRegEmployeeID);
			}
		}

		return result;
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

	@Override
	public Optional<EmpRegHistory> getLastRegHistory(String registeredEmployeeID) {
		return this.queryProxy().find(new PpedtEmployeeRegistrationHistoryPk(registeredEmployeeID),
				PpedtEmployeeRegistrationHistory.class).map(x -> toDomain(x));
	}

}
