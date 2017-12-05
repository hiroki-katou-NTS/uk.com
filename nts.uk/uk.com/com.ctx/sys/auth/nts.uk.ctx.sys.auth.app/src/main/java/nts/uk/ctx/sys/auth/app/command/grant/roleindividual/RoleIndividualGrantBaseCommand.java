package nts.uk.ctx.sys.auth.app.command.grant.roleindividual;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.sys.auth.dom.grant.roleindividual.RoleIndividualGrant;
import nts.uk.ctx.sys.auth.dom.role.RoleType;
@Getter
@Setter
public class RoleIndividualGrantBaseCommand {
	/** The user id. */
	// ユーザID
	private String userID;

	/** The role id. */
	// ロールID
	private String roleID;

	/** The company id. */
	// 会社ID
	private String companyID;

	/** The role type. */
	// ロール種類
	private RoleType roleType;

	/** The valid period. */
	// 有効期間
	private GeneralDate startValidPeriod;
	
	private GeneralDate endValidPeriod;
	//Screen C setting
	private boolean setRoleAdminFlag;
	
	String decisionCompanyID;

	public RoleIndividualGrant toDomain(){
		return RoleIndividualGrant.createFromJavaType(
				userID,
				roleID,
				companyID,
				roleType.value,
				startValidPeriod,
				endValidPeriod);
	}


}
