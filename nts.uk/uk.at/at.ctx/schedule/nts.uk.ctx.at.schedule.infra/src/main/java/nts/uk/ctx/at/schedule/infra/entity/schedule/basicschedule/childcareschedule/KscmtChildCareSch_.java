/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.schedule.basicschedule.childcareschedule;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KscmtChildCareSch_.
 */
@StaticMetamodel(KscmtChildCareSch.class)
public class KscmtChildCareSch_ {
	
    /** The kscmt child care sch PK. */
    public static volatile SingularAttribute<KscmtChildCareSch, KscmtChildCareSchPK> kscmtChildCareSchPK;
    
    /** The str time. */
    public static volatile SingularAttribute<KscmtChildCareSch, Integer> strTime;
    
    /** The str day atr. */
    public static volatile SingularAttribute<KscmtChildCareSch, Integer> strDayAtr;
    
    /** The end time. */
    public static volatile SingularAttribute<KscmtChildCareSch, Integer> endTime;
    
    /** The end day atr. */
    public static volatile SingularAttribute<KscmtChildCareSch, Integer> endDayAtr;
    
    /** The child care atr. */
    public static volatile SingularAttribute<KscmtChildCareSch, Integer> childCareAtr;
}
