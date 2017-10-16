/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.schedule.basicschedule.childcareschedule;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KscmtChildCareSchedule_.
 */
@StaticMetamodel(KscmtChildCareSchedule.class)
public class KscmtChildCareSchedule_ {
	
    /** The exe id. */
    public static volatile SingularAttribute<KscmtChildCareSchedule, KscmtChildCareSchedulePK> kscmtChildCareSchedulePK;
    
    /** The schcare time start. */
    public static volatile SingularAttribute<KscmtChildCareSchedule, Integer> schcareTimeStart;
    
    /** The schcare dayatr start. */
    public static volatile SingularAttribute<KscmtChildCareSchedule, Integer> schcareDayatrStart;
    
    /** The schcare time end. */
    public static volatile SingularAttribute<KscmtChildCareSchedule, Integer> schcareTimeEnd;
    
    /** The schcare dayatr end. */
    public static volatile SingularAttribute<KscmtChildCareSchedule, Integer> schcareDayatrEnd;
    
    /** The child care atr. */
    public static volatile SingularAttribute<KscmtChildCareSchedule, Integer> childCareAtr;
}
