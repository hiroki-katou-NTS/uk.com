/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.shift.estimate.personal;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KscmtEstTimePerSetPK_.
 */
@StaticMetamodel(KscmtEstTimePerSetPK.class)
public class KscmtEstTimePerSetPK_ {

	/** The sid. */
	public static volatile SingularAttribute<KscmtEstTimePerSetPK, String> sid;
	
	/** The target year. */
	public static volatile SingularAttribute<KscmtEstTimePerSetPK, Integer> targetYear;
	
	/** The target cls. */
	public static volatile SingularAttribute<KscmtEstTimePerSetPK, Integer> targetCls;

}