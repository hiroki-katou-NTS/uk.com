/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.schedule.basicschedule.personalfee;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KscdtScheFeeTime_.
 */
@StaticMetamodel(KscdtScheFeeTime.class)
public class KscdtScheFeeTime_ {
	
    /** The kscdt sche fee time PK. */
    public static volatile SingularAttribute<KscdtScheFeeTime, KscdtScheFeeTimePK> kscdtScheFeeTimePK;
    
    /** The person fee time. */
    public static volatile SingularAttribute<KscdtScheFeeTime, Integer> personFeeTime;
    
}
