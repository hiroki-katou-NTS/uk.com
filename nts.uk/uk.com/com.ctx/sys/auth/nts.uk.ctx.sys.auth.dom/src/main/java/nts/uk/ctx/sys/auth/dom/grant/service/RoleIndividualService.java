package nts.uk.ctx.sys.auth.dom.grant.service;

import java.util.List;

import nts.uk.ctx.sys.auth.dom.adapter.company.CompanyImport;
import nts.uk.ctx.sys.auth.dom.grant.roleindividual.RoleIndividualGrant;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

public interface RoleIndividualService {
	
	List<RoleIndividualGrant> selectRoleType(String companyID , int roleType);
	
	List<CompanyImport> selectCompany();

	boolean checkSysAdmin(String userID, DatePeriod validPeriod);
	

}
