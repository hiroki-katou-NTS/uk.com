/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.executionlog;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KscmtSchErrorLog_.
 */
@StaticMetamodel(KscdtScheErrLog.class)
public class KscdtScheErrLog_ {

	/** The kscdt sche err log PK. */
	public static volatile SingularAttribute<KscdtScheErrLog, KscdtScheErrLogPK> kscdtScheErrLogPK;

	/** The err content. */
	public static volatile SingularAttribute<KscdtScheErrLog, String> errContent;

}
