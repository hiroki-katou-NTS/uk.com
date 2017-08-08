/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.entity.login;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;


@StaticMetamodel(SgwdtContract.class)
public class SgwdtContract_ {
    
    /** The contract cd. */
    public static volatile SingularAttribute<SgwdtContract, Long> contractCd;
}
