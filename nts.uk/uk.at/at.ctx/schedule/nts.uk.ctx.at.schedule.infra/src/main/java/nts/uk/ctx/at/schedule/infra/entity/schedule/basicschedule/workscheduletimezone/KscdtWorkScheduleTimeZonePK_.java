/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.schedule.basicschedule.workscheduletimezone;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import nts.arc.time.GeneralDate;

/**
 * The Class KscdtWorkScheduleTimeZonePK_.
 */
@StaticMetamodel(KscdtWorkScheduleTimeZonePK.class)
public class KscdtWorkScheduleTimeZonePK_ {
	
    /** The s id. */
    public static volatile SingularAttribute<KscdtWorkScheduleTimeZonePK, String> sId;
    
    /** The date. */
    public static volatile SingularAttribute<KscdtWorkScheduleTimeZonePK, GeneralDate> date;
    
    /** The schedule cnt. */
    public static volatile SingularAttribute<KscdtWorkScheduleTimeZonePK, Integer> scheduleCnt;
        
}
