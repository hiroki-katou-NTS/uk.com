/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.worktime.flexset;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KshmtFlexWorkSetPK_.
 */
@StaticMetamodel(KshmtFlexWorkSetPK.class)
public class KshmtFlexWorkSetPK_ { 

    /** The cid. */
    public static volatile SingularAttribute<KshmtFlexWorkSetPK, String> cid;
    
    /** The worktime cd. */
    public static volatile SingularAttribute<KshmtFlexWorkSetPK, String> worktimeCd;
    
}