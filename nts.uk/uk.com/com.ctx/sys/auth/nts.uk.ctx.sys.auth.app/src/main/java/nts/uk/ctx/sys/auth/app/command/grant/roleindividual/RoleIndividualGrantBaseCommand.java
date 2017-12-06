package nts.uk.ctx.sys.auth.app.command.grant.roleindividual;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.sys.auth.dom.grant.roleindividual.RoleIndividualGrant;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RoleIndividualGrantBaseCommand {
	/** The user id. */
	// ユーザID
	protected String userID;

	/** The company id. */
	// 会社ID
	protected String companyID;

	/** The role type. */
	// ロール種類
	protected int roleType;

	/** The valid period. */
	// 有効期間
	protected GeneralDate startValidPeriod;
	
	protected GeneralDate endValidPeriod;
	
	//Screen C setting
	protected boolean setRoleAdminFlag;
	
	protected String decisionCompanyID;

	public RoleIndividualGrant toDomain(String roleID) {
		return RoleIndividualGrant.createFromJavaType(userID, roleID, companyID, roleType, startValidPeriod, endValidPeriod);
	}

	
}
