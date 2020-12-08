/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.shift.estimate.company;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KscmtEstDaysComSetPK_.
 */
@StaticMetamodel(KscmtEstDaysComSetPK.class)
public class KscmtEstDaysComSetPK_ {

	/** The cid. */
	public static volatile SingularAttribute<KscmtEstDaysComSetPK, String> cid;
	
	/** The target year. */
	public static volatile SingularAttribute<KscmtEstDaysComSetPK, Integer> targetYear;
	
	/** The target cls. */
	public static volatile SingularAttribute<KscmtEstDaysComSetPK, Integer> targetCls;
	
}