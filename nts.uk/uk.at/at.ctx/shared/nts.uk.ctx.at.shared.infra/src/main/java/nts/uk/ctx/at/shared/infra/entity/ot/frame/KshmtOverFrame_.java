/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.ot.frame;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import nts.uk.ctx.at.shared.infra.entity.ot.frame.KshmtOverFramePK;

/**
 * The Class KshmtOverFrame_.
 */
@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-11-30T09:30:55")
@StaticMetamodel(KshmtOverFrame.class)
public class KshmtOverFrame_ { 

    /** The trans fr name. */
    public static volatile SingularAttribute<KshmtOverFrame, String> transFrName;
    
    /** The use atr. */
    public static volatile SingularAttribute<KshmtOverFrame, Short> useAtr;
    
    /** The kshst overtime frame PK. */
    public static volatile SingularAttribute<KshmtOverFrame, KshmtOverFramePK> kshmtOverFramePK;
    
    /** The exclus ver. */
    public static volatile SingularAttribute<KshmtOverFrame, Integer> exclusVer;
    
    /** The ot fr name. */
    public static volatile SingularAttribute<KshmtOverFrame, String> otFrName;

}