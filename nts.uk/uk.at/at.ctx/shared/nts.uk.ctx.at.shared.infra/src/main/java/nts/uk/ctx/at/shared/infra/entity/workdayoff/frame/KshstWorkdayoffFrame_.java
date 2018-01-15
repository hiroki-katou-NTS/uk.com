/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.workdayoff.frame;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import nts.uk.ctx.at.shared.infra.entity.workdayoff.frame.KshstWorkdayoffFramePK;

/**
 * The Class KshstWorkdayoffFrame_.
 */
@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-11-30T09:30:55")
@StaticMetamodel(KshstWorkdayoffFrame.class)
public class KshstWorkdayoffFrame_ { 

    /** The trans fr name. */
    public static volatile SingularAttribute<KshstWorkdayoffFrame, String> transFrName;
    
    /** The kshst workdayoff frame PK. */
    public static volatile SingularAttribute<KshstWorkdayoffFrame, KshstWorkdayoffFramePK> kshstWorkdayoffFramePK;
    
    /** The use atr. */
    public static volatile SingularAttribute<KshstWorkdayoffFrame, Short> useAtr;
    
    /** The exclus ver. */
    public static volatile SingularAttribute<KshstWorkdayoffFrame, Integer> exclusVer;
    
    /** The wdo fr name. */
    public static volatile SingularAttribute<KshstWorkdayoffFrame, String> wdoFrName;

}