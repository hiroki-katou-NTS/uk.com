/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.schedule.basicschedule.childcareschedule;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KscdtScheChildCare_.
 */
@StaticMetamodel(KscdtScheChildCare.class)
public class KscdtScheChildCare_ {
	
    /** The kscmt child care sch PK. */
    public static volatile SingularAttribute<KscdtScheChildCare, KscdtScheChildCarePK> kscdtScheChildCarePK;
    
    /** The str time. */
    public static volatile SingularAttribute<KscdtScheChildCare, Integer> strTime;
    
    /** The str day atr. */
    public static volatile SingularAttribute<KscdtScheChildCare, Integer> strDayAtr;
    
    /** The end time. */
    public static volatile SingularAttribute<KscdtScheChildCare, Integer> endTime;
    
    /** The end day atr. */
    public static volatile SingularAttribute<KscdtScheChildCare, Integer> endDayAtr;
    
    /** The child care atr. */
    public static volatile SingularAttribute<KscdtScheChildCare, Integer> childCareAtr;
}
