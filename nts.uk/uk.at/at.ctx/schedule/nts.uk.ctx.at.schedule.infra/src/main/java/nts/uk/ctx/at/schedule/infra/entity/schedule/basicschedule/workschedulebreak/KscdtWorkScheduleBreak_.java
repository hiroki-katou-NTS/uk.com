/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.schedule.basicschedule.workschedulebreak;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KscdtWorkScheduleTimeZone_.
 */
@StaticMetamodel(KscdtWorkScheduleBreak.class)
public class KscdtWorkScheduleBreak_ {
	
    /** The kscdt work schedule time zone pk. */
    public static volatile SingularAttribute<KscdtWorkScheduleBreak, KscdtWorkScheduleBreakPK> kscdtWorkScheduleBreakPk;
    
    /** The schedule start clock. */
    public static volatile SingularAttribute<KscdtWorkScheduleBreak, Integer> scheduleStartClock;
    
    /** The schedule end clock. */
    public static volatile SingularAttribute<KscdtWorkScheduleBreak, Integer> scheduleEndClock;    
}
