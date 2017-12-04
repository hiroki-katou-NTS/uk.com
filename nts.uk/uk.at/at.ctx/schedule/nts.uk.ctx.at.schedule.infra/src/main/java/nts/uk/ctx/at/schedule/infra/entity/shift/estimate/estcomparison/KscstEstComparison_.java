/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.shift.estimate.estcomparison;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KscstEstComparison_.
 */
@StaticMetamodel(KscstEstComparison.class)
public class KscstEstComparison_ {

	/** The cid. */
	public static volatile SingularAttribute<KscstEstComparison, String> cid;
	
	/** The comparison atr. */
	public static volatile SingularAttribute<KscstEstComparison, Integer> comparisonAtr;
}
