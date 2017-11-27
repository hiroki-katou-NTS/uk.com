/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.schedule.basicschedule.workscheduletimezone;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KscdtWorkScheduleTimeZone_.
 */
@StaticMetamodel(KscdtWorkScheduleTimeZone.class)
public class KscdtWorkScheduleTimeZone_ {
	
    /** The kscdt work schedule time zone pk. */
    public static volatile SingularAttribute<KscdtWorkScheduleTimeZone, KscdtWorkScheduleTimeZonePK> kscdtWorkScheduleTimeZonePk;
    
    /** The bounce atr. */
    public static volatile SingularAttribute<KscdtWorkScheduleTimeZone, Integer> bounceAtr;
    
    /** The schedule start clock. */
    public static volatile SingularAttribute<KscdtWorkScheduleTimeZone, Integer> scheduleStartClock;
    
    /** The schedule start day atr. */
    public static volatile SingularAttribute<KscdtWorkScheduleTimeZone, Integer> scheduleStartDayAtr;
    
    /** The schedule end clock. */
    public static volatile SingularAttribute<KscdtWorkScheduleTimeZone, Integer> scheduleEndClock;
    
    /** The schedule end day atr. */
    public static volatile SingularAttribute<KscdtWorkScheduleTimeZone, Integer> scheduleEndDayAtr;
    
}
