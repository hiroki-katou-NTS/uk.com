/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.screen.com.infra.query.employee;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import entity.employeeinfo.BsydtEmployee;
import entity.person.info.BpsdtPerson;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.basic.infra.entity.company.organization.employee.department.BsymtDepartment;
import nts.uk.ctx.bs.employee.infra.entity.workplace_old.CwpmtWorkplace;

/**
 * The Class JpaEmployeeSearchQueryRepository.
 */
@Stateless
public class JpaEmployeeSearchQueryRepository extends JpaRepository implements EmployeeSearchQueryRepository{
	
	/** The Constant SEARCH_QUERY_STRING. */
	private static final String SEARCH_QUERY_STRING = "SELECT e, p, wp, d From BsydtEmployee e "
			+ "LEFT JOIN BpsdtPerson p ON e.personId = p.bpsdtPersonPk.pId "
			+ "LEFT JOIN KmnmtAffiliWorkplaceHist h ON e.bsydtEmployeePk.sId = h.kmnmtAffiliWorkplaceHistPK.empId"
			+ "	AND h.kmnmtAffiliWorkplaceHistPK.strD <= :baseDate"
			+ " AND h.endD >= :baseDate "
			+ "LEFT JOIN CwpmtWorkplace wp ON  wp.cwpmtWorkplacePK.wkpid = h.kmnmtAffiliWorkplaceHistPK.wkpId "
			+ "LEFT JOIN BsymtAffiDepartment dh ON dh.sid = e.bsydtEmployeePk.sId"
			+ " AND dh.strD <= :baseDate"
			+ " AND dh.endD >= :baseDate "
			+ "LEFT JOIN BsymtDepartment d ON d.id = dh.depId "
			+ "WHERE e.employeeCode = :empCode";

	/* (non-Javadoc)
	 * @see nts.uk.screen.com.infra.query.employee.EmployeeSearchQueryRepository
	 * #findInAllEmployee(java.lang.String, nts.uk.screen.com.infra.query.employee.System, nts.arc.time.GeneralDate)
	 */
	@Override
	public Optional<Kcp009EmployeeSearchData> findInAllEmployee(String code, System system, GeneralDate baseDate) {
		Object[] resultQuery = null;
		try {
			resultQuery = (Object[]) this.getEntityManager().createQuery("SELECT e, p, wp, d FROM BsydtEmployee e "
					+ "LEFT JOIN BpsdtPerson p ON e.personId = p.bpsdtPersonPk.pId "
					+ "LEFT JOIN KmnmtAffiliWorkplaceHist h ON h.kmnmtAffiliWorkplaceHistPK.empId = e.bsydtEmployeePk.sId"
					+ "	AND h.kmnmtAffiliWorkplaceHistPK.strD <= :baseDate"
					+ " AND h.endD >= :baseDate "
					+ "LEFT JOIN CwpmtWorkplace wp ON  wp.cwpmtWorkplacePK.wkpid = h.kmnmtAffiliWorkplaceHistPK.wkpId "
					+ "LEFT JOIN BsymtAffiDepartment dh ON dh.sid = e.bsydtEmployeePk.sId "
					+ " AND dh.strD <= :baseDate"
					+ " AND dh.endD >= :baseDate "
					+ "LEFT JOIN BsymtDepartment d ON d.id = dh.depId "
					+ "WHERE e.employeeCode = :empCode")
				.setParameter("baseDate", baseDate)
				.setParameter("empCode", code)
				.getSingleResult();
		} catch (NoResultException e) {
			return Optional.empty();
		}
		
		// Convert query data.
		BsydtEmployee employee = (BsydtEmployee) resultQuery[0];
		BpsdtPerson person = (BpsdtPerson) resultQuery[1];
		CwpmtWorkplace workplace = resultQuery[2] == null ? null : (CwpmtWorkplace) resultQuery[2];
		BsymtDepartment department = resultQuery[3] == null ? null : (BsymtDepartment) resultQuery[3];
		
		switch (system) {
		case Employment:
			// Get work place.
			return Optional.of(Kcp009EmployeeSearchData.builder()
					.employeeId(employee.bsydtEmployeePk.sId)
					.employeeCode(employee.employeeCode)
					.businessName(person.businessName)
					.orgName(workplace.getWkpname())
					.build());
		default:
			// Get department
			return Optional.of(Kcp009EmployeeSearchData.builder()
					.employeeId(employee.bsydtEmployeePk.sId)
					.employeeCode(employee.employeeCode)
					.businessName(person.businessName)
					.orgName(department.getName())
					.build());
		}
	}

}
