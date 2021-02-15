/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.ot.frame;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import nts.uk.ctx.at.shared.infra.entity.ot.frame.KshstOvertimeFramePK;

/**
 * The Class KshstOvertimeFrame_.
 */
@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-11-30T09:30:55")
@StaticMetamodel(KshstOvertimeFrame.class)
public class KshstOvertimeFrame_ { 

    /** The trans fr name. */
    public static volatile SingularAttribute<KshstOvertimeFrame, String> transFrName;
    
    /** The use atr. */
    public static volatile SingularAttribute<KshstOvertimeFrame, Short> useAtr;
    
    /** The kshst overtime frame PK. */
    public static volatile SingularAttribute<KshstOvertimeFrame, KshstOvertimeFramePK> kshstOvertimeFramePK;
    
    /** The exclus ver. */
    public static volatile SingularAttribute<KshstOvertimeFrame, Integer> exclusVer;
    
    /** The ot fr name. */
    public static volatile SingularAttribute<KshstOvertimeFrame, String> otFrName;
    
    /** The role. */
    public static volatile SingularAttribute<KshstOvertimeFrame, Short> role;
    
    /** The transfer atr. */
    public static volatile SingularAttribute<KshstOvertimeFrame, Short> transferAtr;
}