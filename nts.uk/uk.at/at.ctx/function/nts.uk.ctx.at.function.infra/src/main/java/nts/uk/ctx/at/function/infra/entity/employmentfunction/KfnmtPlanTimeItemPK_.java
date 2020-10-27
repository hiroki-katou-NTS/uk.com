/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.function.infra.entity.employmentfunction;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KfnmtPlanTimeItemPK_.
 */
@StaticMetamodel(KfnmtPlanTimeItemPK.class)
public class KfnmtPlanTimeItemPK_ { 

    /** The attendance id. */
    public static volatile SingularAttribute<KfnmtPlanTimeItemPK, Short> atdId;
    
    /** The schedule id. */
    public static volatile SingularAttribute<KfnmtPlanTimeItemPK, String> scheduleId;
    
    /** The companyId. */
    public static volatile SingularAttribute<KfnmtPlanTimeItemPK, String> cid;
}