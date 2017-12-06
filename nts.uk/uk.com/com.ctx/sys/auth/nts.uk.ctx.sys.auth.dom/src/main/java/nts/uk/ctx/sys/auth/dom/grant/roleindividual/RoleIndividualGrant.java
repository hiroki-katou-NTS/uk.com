/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.auth.dom.grant.roleindividual;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.sys.auth.dom.role.RoleType;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * The Class RoleIndividualGrant.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoleIndividualGrant extends AggregateRoot {

	/** The user id. */
	// ユーザID
	private String userId;

	/** The role id. */
	// ロールID
	private String roleId;

	/** The company id. */
	// 会社ID
	private String companyId;

	/** The role type. */
	// ロール種類
	private RoleType roleType;

	/** The valid period. */
	// 有効期間
	private DatePeriod validPeriod;
	
	public static RoleIndividualGrant createFromJavaType(String userId, String roleId, String companyId,int roleType, GeneralDate validPeriodStart,GeneralDate validPeriodEnd) {
		return new RoleIndividualGrant(userId,
				roleId,
				companyId,
				EnumAdaptor.valueOf(roleType, RoleType.class),
				new  DatePeriod(validPeriodStart, validPeriodEnd) );
	}

}
