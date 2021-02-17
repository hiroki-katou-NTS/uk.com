/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.infra.entity.securitypolicy.lockoutdata;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import nts.arc.time.GeneralDateTime;

/**
 * The Class SgwmtLogoutDataPK_.
 */
@StaticMetamodel(SgwmtLockoutDataPK.class)
public class SgwmtLockoutDataPK_ {

	/** The sgwmt logout data PK. */
	public static volatile SingularAttribute<SgwmtLockoutDataPK, String> userId;

	/** The contract cd. */
	public static volatile SingularAttribute<SgwmtLockoutDataPK, String> contractCd;
	
	/** The lockout date time. */
    public static volatile SingularAttribute<SgwdtLockout, GeneralDateTime> lockoutDateTime;
}
