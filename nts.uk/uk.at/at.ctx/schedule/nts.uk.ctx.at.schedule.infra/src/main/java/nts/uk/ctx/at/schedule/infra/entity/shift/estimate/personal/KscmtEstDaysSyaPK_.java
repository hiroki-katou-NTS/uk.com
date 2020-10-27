/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.shift.estimate.personal;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KscmtEstDaysSyaPK_.
 */
@StaticMetamodel(KscmtEstDaysSyaPK.class)
public class KscmtEstDaysSyaPK_ {

	/** The sid. */
	public static volatile SingularAttribute<KscmtEstDaysSyaPK, String> sid;
	
	/** The target year. */
	public static volatile SingularAttribute<KscmtEstDaysSyaPK, Integer> targetYear;
	
	/** The target cls. */
	public static volatile SingularAttribute<KscmtEstDaysSyaPK, Integer> targetCls;
	
}