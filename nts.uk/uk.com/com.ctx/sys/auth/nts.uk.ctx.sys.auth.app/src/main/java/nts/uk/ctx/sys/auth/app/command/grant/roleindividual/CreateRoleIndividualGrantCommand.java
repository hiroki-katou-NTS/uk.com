package nts.uk.ctx.sys.auth.app.command.grant.roleindividual;

import nts.uk.ctx.sys.auth.dom.grant.roleindividual.RoleIndividualGrant;

/**
 * 
 * @author hieult
 *
 */
public class CreateRoleIndividualGrantCommand extends RoleIndividualGrantBaseCommand {
	
	public RoleIndividualGrant toDomain(String roleID) {
		return RoleIndividualGrant.createFromJavaType(userID, roleID, companyID, roleType, startValidPeriod, endValidPeriod);
	}

}
