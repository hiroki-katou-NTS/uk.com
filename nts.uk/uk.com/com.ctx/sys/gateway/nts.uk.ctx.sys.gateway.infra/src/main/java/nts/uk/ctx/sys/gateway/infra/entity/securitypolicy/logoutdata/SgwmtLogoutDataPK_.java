/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.infra.entity.securitypolicy.logoutdata;

import java.util.Date;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class SgwmtLogoutDataPK_.
 */
@StaticMetamodel(SgwmtLogoutDataPK.class)
public class SgwmtLogoutDataPK_ {

	/** The sgwmt logout data PK. */
	public static volatile SingularAttribute<SgwmtLogoutDataPK, String> userId;

	/** The contract cd. */
	public static volatile SingularAttribute<SgwmtLogoutDataPK, String> contractCd;
	
	/** The login method. */
	public static volatile SingularAttribute<SgwmtLogoutDataPK, Integer> loginMethod;
	
	/** The logout date time. */
	public static volatile SingularAttribute<SgwmtLogoutDataPK, Date> logoutDateTime;
}
