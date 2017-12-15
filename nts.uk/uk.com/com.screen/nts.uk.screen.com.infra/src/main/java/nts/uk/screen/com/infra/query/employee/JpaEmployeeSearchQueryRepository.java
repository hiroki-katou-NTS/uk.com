/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.screen.com.infra.query.employee;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;

/**
 * The Class JpaEmployeeSearchQueryRepository.
 */
@Stateless
public class JpaEmployeeSearchQueryRepository extends JpaRepository implements EmployeeSearchQueryRepository{
	
	/** The Constant SEARCH_QUERY_STRING. */
	private static final String SEARCH_QUERY_STRING = "SELECT e, p, wp, d From BsymtEmployee e "
			+ " LEFT JOIN BpsmtPerson p ON e.personId = p.bpsmtPersonPk.pId "
			+ " LEFT JOIN KmnmtAffiliWorkplaceHist h ON h.kmnmtAffiliWorkplaceHistPK.empId = e.bsymtEmployeePk.sId "
			+ "	AND h.kmnmtAffiliWorkplaceHistPK.strD <= :baseDate "
			+ " AND h.endD >= :baseDate "
			+ " LEFT JOIN BsymtWorkplaceInfo wp ON wp.bsymtWorkplaceInfoPK.wkpid = h.kmnmtAffiliWorkplaceHistPK.wkpId "
			+ " AND wp.bsymtWorkplaceInfoPK.cid = e.companyId "
			+ " LEFT JOIN BsymtWorkplaceHist wphist ON wp.bsymtWorkplaceInfoPK.historyId = wphist.bsymtWorkplaceHistPK.historyId "
			+ " AND wphist.strD <= :baseDate "
			+ " AND wphist.endD >= :baseDate "
			+ " LEFT JOIN BsymtAffiDepartment ad ON ad.sid = e.bsymtEmployeePk.sId "
			+ " AND ad.strD <= :baseDate"
			+ " AND ad.endD >= :baseDate "
			+ " LEFT JOIN BsymtDepartmentHist dh ON dh.bsymtDepartmentHistPK.depId = ad.depId "
			+ " AND dh.strD <= :baseDate "
			+ " AND dh.endD >= :baseDate "
			+ " AND dh.bsymtDepartmentHistPK.cid = e.companyId "
			+ " LEFT JOIN BsymtDepartmentInfo d ON d.bsymtDepartmentInfoPK.depId = dh.bsymtDepartmentHistPK.depId "
			+ " AND d.bsymtDepartmentInfoPK.cid = e.companyId "
			+ " AND d.bsymtDepartmentInfoPK.histId = dh.bsymtDepartmentHistPK.histId "
			+ " WHERE e.employeeCode = :empCode "
			+ " AND e.companyId = :companyId ";

	/* (non-Javadoc)
	 * @see nts.uk.screen.com.infra.query.employee.EmployeeSearchQueryRepository
	 * #findInAllEmployee(java.lang.String, nts.uk.screen.com.infra.query.employee.System, nts.arc.time.GeneralDate)
	 */
	@Override
	public Optional<Kcp009EmployeeSearchData> findInAllEmployee(String code, System system, GeneralDate baseDate, String companyId) {
//		Object[] resultQuery = null;
//		try {
//			resultQuery = (Object[]) this.getEntityManager().createQuery(SEARCH_QUERY_STRING)
//				.setParameter("baseDate", baseDate)
//				.setParameter("empCode", code)
//				.setParameter("companyId", companyId)
//				.getSingleResult();
//		} catch (NoResultException e) {
			return Optional.empty();
//		}
//		
//		// Convert query data.
//		BsymtEmployee employee = (BsymtEmployee) resultQuery[0];
//		BpsmtPerson person = (BpsmtPerson) resultQuery[1];
//		BsymtWorkplaceInfo workplace = resultQuery[2] == null ? null : (BsymtWorkplaceInfo) resultQuery[2];
//		BsymtDepartmentInfo department = resultQuery[3] == null ? null : (BsymtDepartmentInfo) resultQuery[3];
//		
//		switch (system) {
//		case Employment:
//			// Get work place.
//			return Optional.of(Kcp009EmployeeSearchData.builder()
//					.employeeId(employee.bsymtEmployeePk.sId)
//					.employeeCode(employee.employeeCode)
//					.businessName(person.businessName)
//					.orgName(workplace != null ? workplace.getWkpName() : null)
//					.build());
//		default:
//			// Get department
//			return Optional.of(Kcp009EmployeeSearchData.builder()
//					.employeeId(employee.bsymtEmployeePk.sId)
//					.employeeCode(employee.employeeCode)
//					.businessName(person.businessName)
//					.orgName(department != null ? department.getName() : null)
//					.build());
//		}
	}

}
