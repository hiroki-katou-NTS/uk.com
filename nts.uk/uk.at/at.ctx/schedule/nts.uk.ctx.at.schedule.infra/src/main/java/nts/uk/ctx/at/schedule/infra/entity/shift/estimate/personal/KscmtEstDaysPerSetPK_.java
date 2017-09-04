/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.shift.estimate.personal;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KscmtEstDaysPerSetPK_.
 */
@StaticMetamodel(KscmtEstDaysPerSetPK.class)
public class KscmtEstDaysPerSetPK_ {

	/** The sid. */
	public static volatile SingularAttribute<KscmtEstDaysPerSetPK, String> sid;
	
	/** The target year. */
	public static volatile SingularAttribute<KscmtEstDaysPerSetPK, Integer> targetYear;
	
	/** The target cls. */
	public static volatile SingularAttribute<KscmtEstDaysPerSetPK, Integer> targetCls;
	
}