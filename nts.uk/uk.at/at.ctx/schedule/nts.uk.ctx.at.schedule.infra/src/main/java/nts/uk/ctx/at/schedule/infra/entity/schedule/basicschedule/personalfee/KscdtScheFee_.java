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
@StaticMetamodel(KscdtScheFee.class)
public class KscdtScheFee_ {
	
    /** The kscdt sche fee PK. */
    public static volatile SingularAttribute<KscdtScheFee, KscdtScheFeePK> kscdtScheFeePK;
    
    /** The personal pee amount. */
    public static volatile SingularAttribute<KscdtScheFee, Integer> personalPeeAmount;
    
}
