/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.worktime.flexset;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KshmtFlexRestSet_.
 */
@StaticMetamodel(KshmtFlexRestSet.class)
public class KshmtFlexRestSet_ { 

    /** The kshmt flex rest set PK. */
    public static volatile SingularAttribute<KshmtFlexRestSet, KshmtFlexRestSetPK> kshmtFlexRestSetPK;
    
    /** The is refer rest time. */
    public static volatile SingularAttribute<KshmtFlexRestSet, Integer> isReferRestTime;
    
    /** The user private go out rest. */
    public static volatile SingularAttribute<KshmtFlexRestSet, Integer> userPrivateGoOutRest;
    
    /** The use atr. */
    public static volatile SingularAttribute<KshmtFlexRestSet, Integer> userAssoGoOutRest;
    
    /** The fixed rest calc method. */
    public static volatile SingularAttribute<KshmtFlexRestSet, Integer> fixedRestCalcMethod;
    
    /** The use stamp. */
    public static volatile SingularAttribute<KshmtFlexRestSet, Integer> useStamp;
    
    /** The use stamp calc method. */
    public static volatile SingularAttribute<KshmtFlexRestSet, Integer> useStampCalcMethod;
    
    /** The time manager set atr. */
    public static volatile SingularAttribute<KshmtFlexRestSet, Integer> timeManagerSetAtr;
    
    /** The calculate method. */
    public static volatile SingularAttribute<KshmtFlexRestSet, Integer> calculateMethod;
    
    /** The use plural work rest time. */
    public static volatile SingularAttribute<KshmtFlexRestSet, Integer> usePluralWorkRestTime;
    
    /** The common calculate method. */
    public static volatile SingularAttribute<KshmtFlexRestSet, Integer> commonCalculateMethod;
    

}