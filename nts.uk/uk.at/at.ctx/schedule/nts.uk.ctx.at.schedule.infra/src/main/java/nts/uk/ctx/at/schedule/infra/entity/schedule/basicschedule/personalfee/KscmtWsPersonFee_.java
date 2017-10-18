/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.schedule.basicschedule.personalfee;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KscmtWsPersonFee_.
 */
@StaticMetamodel(KscmtWsPersonFee.class)
public class KscmtWsPersonFee_ {
	
    /** The kscmt ws person fee PK. */
    public static volatile SingularAttribute<KscmtWsPersonFee, KscmtWsPersonFeePK> kscmtWsPersonFeePK;
    
    /** The personal pee amount. */
    public static volatile SingularAttribute<KscmtWsPersonFee, Integer> personalPeeAmount;
    
}
