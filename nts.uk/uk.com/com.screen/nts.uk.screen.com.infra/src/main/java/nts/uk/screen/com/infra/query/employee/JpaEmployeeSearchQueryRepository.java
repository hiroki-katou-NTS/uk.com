package nts.uk.screen.com.infra.query.employee;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.uk.screen.com.infra.query.employee.EmployeeSearchQueryRepository;
import nts.uk.screen.com.infra.query.employee.Kcp009EmployeeSearchData;
import nts.uk.screen.com.infra.query.employee.System;

@Stateless
public class JpaEmployeeSearchQueryRepository implements EmployeeSearchQueryRepository{
	
	private static final String SEARCH_QUERY_STRING = "SELECT e, p, wp, d From BsydtEmployee e "
			+ "LEFT JOIN BpsdtPerson p ON e.personId = p.bpsdtPersonPk.pId "
			+ "LEFT JOIN KmnmtAffiliWorkplaceHist h ON e.bsydtEmployeePk.sId = h.kmnmtAffiliWorkplaceHistPK.empId"
			+ "	AND h.kmnmtAffiliWorkplaceHistPK.strD <= :baseDate"
			+ " AND h.endD >= :baseDate "
			+ "LEFT JOIN CwpmtWorkplace wp ON  wp.cwpmtWorkplacePK.wkpid = h.kmnmtAffiliWorkplaceHistPK.wkpId "
			+ "LEFT JOIN BcmmtAffiDepartment dh ON dh.sid = e.bsydtEmployeePk.sId"
			+ " AND dh.strD <= :baseDate"
			+ " AND dh.endD >= :baseDate "
			+ "LEFT JOIN ";

	@Override
	public Optional<Kcp009EmployeeSearchData> findInAllEmployee(String code, System system) {
		// TODO Auto-generated method stub
		return null;
	}

}
