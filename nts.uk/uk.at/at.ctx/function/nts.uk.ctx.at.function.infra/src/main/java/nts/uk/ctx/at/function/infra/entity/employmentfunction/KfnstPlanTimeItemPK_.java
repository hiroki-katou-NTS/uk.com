/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.function.infra.entity.employmentfunction;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KfnstPlanTimeItemPK_.
 */
@StaticMetamodel(KfnstPlanTimeItemPK.class)
public class KfnstPlanTimeItemPK_ { 

    /** The attendance id. */
    public static volatile SingularAttribute<KfnstPlanTimeItemPK, Short> atdId;
    
    /** The schedule id. */
    public static volatile SingularAttribute<KfnstPlanTimeItemPK, String> scheduleId;
    
    /** The companyId. */
    public static volatile SingularAttribute<KfnstPlanTimeItemPK, String> cid;
}