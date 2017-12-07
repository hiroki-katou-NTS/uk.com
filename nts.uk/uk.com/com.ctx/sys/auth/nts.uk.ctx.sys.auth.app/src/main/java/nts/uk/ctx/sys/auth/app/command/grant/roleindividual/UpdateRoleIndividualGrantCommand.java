package nts.uk.ctx.sys.auth.app.command.grant.roleindividual;

import lombok.EqualsAndHashCode;
import lombok.Value;
import nts.uk.ctx.sys.auth.dom.grant.roleindividual.RoleIndividualGrant;

@Value
@EqualsAndHashCode(callSuper=false)
public class UpdateRoleIndividualGrantCommand extends RoleIndividualGrantBaseCommand{

	private String roleId;

	public UpdateRoleIndividualGrantCommand(String roleId) {
		super();
		this.roleId = roleId;
	}
	
	public RoleIndividualGrant toDomain() {
		return RoleIndividualGrant.createFromJavaType(userID, this.roleId, companyID, roleType, startValidPeriod, endValidPeriod);
	}

}