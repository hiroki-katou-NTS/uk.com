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
@StaticMetamodel(KscmtSchErrorLog.class)
public class KscmtSchErrorLog_ {

	/** The kscmt sch error log PK. */
	public static volatile SingularAttribute<KscmtSchErrorLog, KscmtSchErrorLogPK> kscmtSchErrorLogPK;

	/** The err content. */
	public static volatile SingularAttribute<KscmtSchErrorLog, String> errContent;

}
