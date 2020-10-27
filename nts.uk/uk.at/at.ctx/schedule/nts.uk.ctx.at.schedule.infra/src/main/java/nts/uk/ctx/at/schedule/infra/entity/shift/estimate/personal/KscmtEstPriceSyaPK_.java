/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.shift.estimate.personal;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KscmtEstPriceSyaPK_.
 */
@StaticMetamodel(KscmtEstPriceSyaPK.class)
public class KscmtEstPriceSyaPK_ {

	/** The sid. */
	public static volatile SingularAttribute<KscmtEstPriceSyaPK, String> sid;
	
	/** The target year. */
	public static volatile SingularAttribute<KscmtEstPriceSyaPK, Integer> targetYear;
	
	/** The target cls. */
	public static volatile SingularAttribute<KscmtEstPriceSyaPK, Integer> targetCls;
		
}