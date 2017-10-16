/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.executionlog;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KscmtSchErrorLogPK_.
 */
@StaticMetamodel(KscmtSchErrorLogPK.class)
public class KscmtSchErrorLogPK_ {

	/** The exe id. */
	public static volatile SingularAttribute<KscmtSchErrorLogPK, String> exeId;

	/** The err content. */
	public static volatile SingularAttribute<KscmtSchErrorLogPK, String> sid;
	
	/** The ymd. */
	public static volatile SingularAttribute<KscmtSchErrorLogPK, String> ymd;

}
