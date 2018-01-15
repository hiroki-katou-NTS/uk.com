package nts.uk.ctx.sys.auth.dom.grant.service;


import java.util.List;

import nts.uk.ctx.sys.auth.dom.grant.roleindividual.RoleIndividualGrant;
import nts.uk.ctx.sys.auth.dom.role.RoleType;

public interface RoleIndividualComService {
	List<RoleIndividualGrant> selectByRoleType( int roleType);
	
	
	
}
