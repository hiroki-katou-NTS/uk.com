/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.dailypattern;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

// TODO: Auto-generated Javadoc
/**
 * The Class KdpstDailyPatternValPK_.
 */
@StaticMetamodel(KdpstDailyPatternValPK.class)
public class KdpstDailyPatternValPK_ {
	   
   	/** The cid. */
    public static volatile SingularAttribute<KdpstDailyPatternValPK, String> cid;
    
    /** The pattern cd. */
    public static volatile SingularAttribute<KdpstDailyPatternValPK, String> patternCd;
    
    /** The disp order. */
    public static volatile SingularAttribute<KdpstDailyPatternValPK, Integer> dispOrder;
}
