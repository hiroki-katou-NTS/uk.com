/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.schedule.basicschedule.personalfee;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import nts.arc.time.GeneralDate;

/**
 * The Class KscmtWsPersonFeeTimePK_.
 */
@StaticMetamodel(KscdtScheFeeTimePK.class)
public class KscdtScheFeeTimePK_ {
	
    /** The kscmt ws person fee time PK. */
    public static volatile SingularAttribute<KscdtScheFeeTimePK, String> sid;
    
    /** The ymd. */
    public static volatile SingularAttribute<KscdtScheFeeTimePK, GeneralDate> ymd;
    
}
