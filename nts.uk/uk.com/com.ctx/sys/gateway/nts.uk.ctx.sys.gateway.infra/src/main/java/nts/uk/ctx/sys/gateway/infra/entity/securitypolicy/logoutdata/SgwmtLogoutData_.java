/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.infra.entity.securitypolicy.logoutdata;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;


/**
 * The Class SgwdtContract_.
 */
@StaticMetamodel(SgwmtLogoutData.class)
public class SgwmtLogoutData_ {
    
    /** The sgwmt logout data PK. */
    public static volatile SingularAttribute<SgwmtLogoutData, SgwmtLogoutDataPK> sgwmtLogoutDataPK;
    
    /** The lock type. */
    public static volatile SingularAttribute<SgwmtLogoutData, Integer> lockType;
}
