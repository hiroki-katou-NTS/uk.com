/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.request.infra.entity.valication.history;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KrqmtVacationHistoryPK_.
 */
@StaticMetamodel(KrqmtVacationHistoryPK.class)
public class KrqmtVacationHistoryPK_ {

	/** The cid. */
	public static volatile SingularAttribute<KrqmtVacationHistoryPK, String> cid;
	
	/** The history id. */
	public static volatile SingularAttribute<KrqmtVacationHistoryPK, String> historyId;
	
	/** The worktype cd. */
	public static volatile SingularAttribute<KrqmtVacationHistoryPK, String> worktypeCd;
}
