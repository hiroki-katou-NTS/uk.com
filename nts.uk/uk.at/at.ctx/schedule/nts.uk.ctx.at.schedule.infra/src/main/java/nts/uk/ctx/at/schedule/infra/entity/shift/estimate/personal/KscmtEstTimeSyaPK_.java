/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.shift.estimate.personal;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KscmtEstTimeSyaPK_.
 */
@StaticMetamodel(KscmtEstTimeSyaPK.class)
public class KscmtEstTimeSyaPK_ {

	/** The sid. */
	public static volatile SingularAttribute<KscmtEstTimeSyaPK, String> sid;
	
	/** The target year. */
	public static volatile SingularAttribute<KscmtEstTimeSyaPK, Integer> targetYear;
	
	/** The target cls. */
	public static volatile SingularAttribute<KscmtEstTimeSyaPK, Integer> targetCls;

}