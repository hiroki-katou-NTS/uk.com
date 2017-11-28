/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.ot.frame;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KshstOvertimeFrame_.
 */
@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-11-17T14:08:22")
@StaticMetamodel(KshstOvertimeFrame.class)

public class KshstOvertimeFrame_ { 

    /** The use atr. */
    public static volatile SingularAttribute<KshstOvertimeFrame, Short> useAtr;
    
    /** The upd ccd. */
    public static volatile SingularAttribute<KshstOvertimeFrame, String> updCcd;
    
    /** The upd pg. */
    public static volatile SingularAttribute<KshstOvertimeFrame, String> updPg;
    
    /** The ot fr name. */
    public static volatile SingularAttribute<KshstOvertimeFrame, String> otFrName;
    
    /** The trans fr name. */
    public static volatile SingularAttribute<KshstOvertimeFrame, String> transFrName;
    
    /** The kshst overtime frame PK. */
    public static volatile SingularAttribute<KshstOvertimeFrame, KshstOvertimeFramePK> kshstOvertimeFramePK;
    
    /** The upd scd. */
    public static volatile SingularAttribute<KshstOvertimeFrame, String> updScd;
    
    /** The exclus ver. */
    public static volatile SingularAttribute<KshstOvertimeFrame, Integer> exclusVer;

}