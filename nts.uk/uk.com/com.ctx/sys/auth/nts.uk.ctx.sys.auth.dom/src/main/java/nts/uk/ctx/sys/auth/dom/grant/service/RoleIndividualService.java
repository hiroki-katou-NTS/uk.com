package nts.uk.ctx.sys.auth.dom.grant.service;

import java.util.List;

import nts.uk.ctx.sys.auth.dom.grant.roleindividual.RoleIndividualGrant;
import nts.uk.ctx.sys.auth.dom.role.RoleType;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

public interface RoleIndividualService {

	boolean isExit();

	boolean checkSysAdmin(String userID, DatePeriod validPeriod);

	void create(boolean setCompanyAdminRoleFlag, String decisionCompanyID, RoleIndividualGrant roleIndividualGrant);

	OutputSetAdminRole setAdminRole(boolean setCompanyAdminRoleFlag, String decisionCompanyID);

	void update(RoleIndividualGrant roleIndividualGrant);

	void deleteRoleIndividualGrant(String userID, String coppanyID, RoleType roleType, DatePeriod validPeriod);
	
    List<RoleIndividualGrant> searchRole(RoleType roleType, String companyID  );


}
