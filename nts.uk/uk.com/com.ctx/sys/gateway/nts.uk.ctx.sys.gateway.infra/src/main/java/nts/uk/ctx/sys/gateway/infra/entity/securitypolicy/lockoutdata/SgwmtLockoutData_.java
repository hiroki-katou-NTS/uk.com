/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.infra.entity.securitypolicy.lockoutdata;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;


/**
 * The Class SgwdtContract_.
 */
@StaticMetamodel(SgwdtLockoutData.class)
public class SgwmtLockoutData_ {
    
    /** The sgwmt logout data PK. */
    public static volatile SingularAttribute<SgwdtLockoutData, SgwdtLockoutDataPK> sgwmtLockoutDataPK;
    
    /** The lock type. */
    public static volatile SingularAttribute<SgwdtLockoutData, Integer> lockType;
    
    /** The login method. */
    public static volatile SingularAttribute<SgwdtLockoutData, Integer> loginMethod;
}
