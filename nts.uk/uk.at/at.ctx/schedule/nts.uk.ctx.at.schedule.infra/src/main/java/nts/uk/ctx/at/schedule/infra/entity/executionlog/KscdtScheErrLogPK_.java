/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.executionlog;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KscdtScheErrLogPK_.
 */
@StaticMetamodel(KscdtScheErrLogPK.class)
public class KscdtScheErrLogPK_ {

	/** The exe id. */
	public static volatile SingularAttribute<KscdtScheErrLogPK, String> exeId;

	/** The sid. */
	public static volatile SingularAttribute<KscdtScheErrLogPK, String> sid;
	
	/** The ymd. */
	public static volatile SingularAttribute<KscdtScheErrLogPK, String> ymd;

}
