/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.executionlog;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KscdtScheExeLogPK_.
 */
@StaticMetamodel(KscdtScheExeLogPK.class)
public class KscdtScheExeLogPK_ {

	 /** The cid. */
    public static volatile SingularAttribute<KscdtScheExeLogPK, String> cid;
    
    /** The exe id. */
    public static volatile SingularAttribute<KscdtScheExeLogPK, String> exeId;
	
}
