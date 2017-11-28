package nts.uk.ctx.sys.auth.dom.grant.service;


import nts.uk.ctx.sys.auth.dom.grant.roleindividual.RoleIndividualGrant;
import nts.uk.ctx.sys.auth.dom.role.RoleType;

public interface RoleIndividualComService {

	boolean isExist(String userID, String companyID, RoleType roleType);

	void create(RoleIndividualGrant roleIndividualGrant);

	void update(RoleIndividualGrant roleIndividualGrant);

	void remove(String userID, String companyID, RoleType roleType);
	
	
	
}
