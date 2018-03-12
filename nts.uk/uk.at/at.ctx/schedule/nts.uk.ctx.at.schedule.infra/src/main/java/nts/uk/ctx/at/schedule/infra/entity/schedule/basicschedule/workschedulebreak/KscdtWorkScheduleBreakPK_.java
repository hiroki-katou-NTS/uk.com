/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.schedule.basicschedule.workschedulebreak;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import nts.arc.time.GeneralDate;

/**
 * The Class KscdtWorkScheduleBreakPK_.
 */
@StaticMetamodel(KscdtWorkScheduleBreakPK.class)
public class KscdtWorkScheduleBreakPK_ {
	
    /** The s id. */
    public static volatile SingularAttribute<KscdtWorkScheduleBreakPK, String> sId;
    
    /** The date. */
    public static volatile SingularAttribute<KscdtWorkScheduleBreakPK, GeneralDate> date;
    
    /** The schedule cnt. */
    public static volatile SingularAttribute<KscdtWorkScheduleBreakPK, Integer> scheduleCnt;
        
}
