package nts.uk.ctx.pereg.infra.repository.reghistory;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pereg.dom.reghistory.EmpRegHistory;
import nts.uk.ctx.pereg.dom.reghistory.EmpRegHistoryRepository;
import nts.uk.ctx.pereg.dom.reghistory.LastEmRegHistory;
import nts.uk.ctx.pereg.infra.entity.reghistory.PpedtEmployeeRegistrationHistory;
import nts.uk.ctx.pereg.infra.entity.reghistory.PpedtEmployeeRegistrationHistoryPk;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class JpaEmpRegHistoryRepository extends JpaRepository implements EmpRegHistoryRepository {

	private static final String SELECT_ALL = "SELECT rh,em.employeeCode FROM PpedtEmployeeRegistrationHistory rh"
			+ " INNER JOIN BsymtEmployeeDataMngInfo em ON rh.lastRegEmployeeID = em.bsymtEmployeeDataMngInfoPk.sId AND em.delStatus = 0";

	private static final String GET_LAST_REG_BY_COMPANY_QUERY_STRING = SELECT_ALL
			+ " WHERE rh.companyId= :companyId  ORDER BY rh.registeredDate DESC ";

	private static final String GET_LAST_REG_BY_EMPLOYEE_ID_QUERY_STRING = SELECT_ALL
			+ " WHERE rh.ppedtEmployeeRegistrationHistoryPk.registeredEmployeeID= :employeeId";

	private static final String GET_REG_HIST_BY_EMPLOYEE_ID_QUERY_STRING = "SELECT rh FROM PpedtEmployeeRegistrationHistory rh WHERE rh.ppedtEmployeeRegistrationHistoryPk.registeredEmployeeID= :employeeId";

	@Override
	public Optional<LastEmRegHistory> getLastRegHistory(String registeredEmployeeID, String companyId) {

		Optional<EmpRegHistory> optEmpHist = this.getLastRegHistory(registeredEmployeeID);

		Optional<EmpRegHistory> optCompHist = this.getLastRegHistoryOfCompany(companyId);

		LastEmRegHistory result = toLastDomain(optEmpHist, optCompHist);
		return Optional
				.ofNullable(result.getLastRegEmployeeID() == null && result.getLastRegEmployeeOfCompanyID() == null
						? null : result);

	}

	private EmpRegHistory toDomain(Object[] entity) {

		PpedtEmployeeRegistrationHistory regHistEntity = (PpedtEmployeeRegistrationHistory) entity[0];

		return EmpRegHistory.createFromJavaType(regHistEntity.ppedtEmployeeRegistrationHistoryPk.registeredEmployeeID,
				regHistEntity.companyId, regHistEntity.registeredDate, regHistEntity.lastRegEmployeeID,
				entity[1].toString());
	}

	private EmpRegHistory toDomain(PpedtEmployeeRegistrationHistory empRegHist) {

		return EmpRegHistory.createFromJavaType(empRegHist.ppedtEmployeeRegistrationHistoryPk.registeredEmployeeID,
				empRegHist.companyId, empRegHist.registeredDate, empRegHist.lastRegEmployeeID, "");
	}

	private Optional<EmpRegHistory> getLastRegHistoryOfCompany(String companyId) {

		return this.getEntityManager().createQuery(GET_LAST_REG_BY_COMPANY_QUERY_STRING, Object[].class)
				.setParameter("companyId", companyId).setMaxResults(1).getResultList().stream().findFirst()
				.map(x -> toDomain(x));

	}

	private LastEmRegHistory toLastDomain(Optional<EmpRegHistory> optEmpHist, Optional<EmpRegHistory> optCompHist) {

		LastEmRegHistory result = new LastEmRegHistory();
		if (optEmpHist.isPresent()) {
			EmpRegHistory empHist = optEmpHist.get();

			result.setCompanyId(empHist.getCompanyId());
			result.setLastRegEmployeeID(empHist.getLastRegEmployeeID());
			result.setRegisteredDate(empHist.getRegisteredDate());
			result.setRegisteredEmployeeID(empHist.getRegisteredEmployeeID());
			result.setLastRegEmployeeCd(empHist.getLastRegEmployeeCd());
		}

		if (optCompHist.isPresent()) {

			EmpRegHistory comHist = optCompHist.get();

			if (!comHist.getLastRegEmployeeID().equals(AppContexts.user().employeeId())) {

				result.setLastRegEmployeeOfCompanyID(comHist.getLastRegEmployeeID());
				result.setLastRegEmployeeOfCompanyCd(comHist.getLastRegEmployeeCd());
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
		this.getEntityManager().flush();

	}

	@Override
	public void update(EmpRegHistory newEmpRegHistory) {

		Optional<PpedtEmployeeRegistrationHistory> optRegHist = this.queryProxy().find(
				new PpedtEmployeeRegistrationHistoryPk(newEmpRegHistory.getRegisteredEmployeeID()),
				PpedtEmployeeRegistrationHistory.class);

		if (optRegHist.isPresent()) {
			this.commandProxy().update(optRegHist.get().updateFromDomain(newEmpRegHistory));
		}
		this.getEntityManager().flush();

	}

	@Override
	public Optional<EmpRegHistory> getLastRegHistory(String registeredEmployeeID) {

		return this.queryProxy().query(GET_LAST_REG_BY_EMPLOYEE_ID_QUERY_STRING, Object[].class)
				.setParameter("employeeId", registeredEmployeeID).getSingle().map(x -> toDomain(x));
	}

	@Override
	public Optional<EmpRegHistory> getRegHistById(String employeeID) {

		return this.queryProxy().query(GET_REG_HIST_BY_EMPLOYEE_ID_QUERY_STRING, PpedtEmployeeRegistrationHistory.class)
				.setParameter("employeeId", employeeID).getSingle().map(x -> toDomain(x));
	}

}
