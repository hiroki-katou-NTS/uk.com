/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.schedule.basicschedule.personalfee;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import nts.arc.time.GeneralDate;

/**
 * The Class KscmtWsPersonFeePK_.
 */
@StaticMetamodel(KscmtWsPersonFeePK.class)
public class KscmtWsPersonFeePK_ {
	
    /** The sid. */
    public static volatile SingularAttribute<KscmtWsPersonFeePK, String> sid;
    
    /** The ymd. */
    public static volatile SingularAttribute<KscmtWsPersonFeePK, GeneralDate> ymd;
    
    /** The no. */
    public static volatile SingularAttribute<KscmtWsPersonFeePK, Integer> no;
    
}
