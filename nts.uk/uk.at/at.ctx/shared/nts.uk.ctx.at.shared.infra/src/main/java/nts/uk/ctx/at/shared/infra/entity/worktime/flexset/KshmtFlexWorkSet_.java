/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.worktime.flexset;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KshmtFlexWorkSet_.
 */
@StaticMetamodel(KshmtFlexWorkSet.class)
public class KshmtFlexWorkSet_ { 

    /** The kshmt flex work set PK. */
    public static volatile SingularAttribute<KshmtFlexWorkSet, KshmtFlexWorkSetPK> kshmtFlexWorkSetPK;
    
    /** The core time str. */
    public static volatile SingularAttribute<KshmtFlexWorkSet, Integer> coreTimeStr;
    
    /** The core time end. */
    public static volatile SingularAttribute<KshmtFlexWorkSet, Integer> coreTimeEnd;
    
    /** The use atr. */
    public static volatile SingularAttribute<KshmtFlexWorkSet, Integer> useAtr;
    
    /** The least work time. */
    public static volatile SingularAttribute<KshmtFlexWorkSet, Integer> leastWorkTime;
    
    /** The deduct from work time. */
    public static volatile SingularAttribute<KshmtFlexWorkSet, Integer> deductFromWorkTime;
    
    /** The especial calc. */
    public static volatile SingularAttribute<KshmtFlexWorkSet, Integer> especialCalc;

}