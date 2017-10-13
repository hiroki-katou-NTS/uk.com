/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.schedule.basicschedule.personalfee;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KscmtWsPersonFeeTime_.
 */
@StaticMetamodel(KscmtWsPersonFeeTime.class)
public class KscmtWsPersonFeeTime_ {
	
    /** The kscmt ws person fee time PK. */
    public static volatile SingularAttribute<KscmtWsPersonFeeTime, KscmtWsPersonFeeTimePK> kscmtWsPersonFeeTimePK;
    
    /** The person fee time. */
    public static volatile SingularAttribute<KscmtWsPersonFeeTime, Integer> personFeeTime;
    
}
