/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.shift.estimate.aggregateset;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KscstPerCostExtraItem_.
 */
@StaticMetamodel(KscmtPerCostExtraItem.class)
public class KscstPerCostExtraItem_ {

	/** The kscst per cost extra item PK. */
	public static volatile SingularAttribute<KscmtPerCostExtraItem, KscstPerCostExtraItemPK> kscstPerCostExtraItemPK;
	
	/** The kscst est aggregate set. */
	public static volatile SingularAttribute<KscmtPerCostExtraItem, KscmtEstAggregate> kscstEstAggregateSet;
}
