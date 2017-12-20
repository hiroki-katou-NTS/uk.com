/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.shift.estimate.aggregateset;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KscstEstAggregateSet_.
 */
@StaticMetamodel(KscstEstAggregateSet.class)
public class KscstEstAggregateSet_ {

	/** The cid. */
	public static volatile SingularAttribute<KscstEstAggregateSet, String> cid;
	
	/** The half day atr. */
	public static volatile SingularAttribute<KscstEstAggregateSet, Integer> halfDayAtr;
	
	/** The year hd atr. */
	public static volatile SingularAttribute<KscstEstAggregateSet, Integer> yearHdAtr;
	
	/** The sphd atr. */
	public static volatile SingularAttribute<KscstEstAggregateSet, Integer> sphdAtr;
	
	/** The havy hd atr. */
	public static volatile SingularAttribute<KscstEstAggregateSet, Integer> havyHdAtr;
	
	/** The kscst per cost extra item. */
	public static volatile ListAttribute<KscstEstAggregateSet, KscstPerCostExtraItem> kscstPerCostExtraItem;
}
