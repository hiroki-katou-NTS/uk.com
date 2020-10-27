/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.shift.estimate.aggregateset;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KscmtPerCostExtraItem_.
 */
@StaticMetamodel(KscmtPerCostExtraItem.class)
public class KscmtPerCostExtraItem_ {

	/** The kscst per cost extra item PK. */
	public static volatile SingularAttribute<KscmtPerCostExtraItem, KscmtPerCostExtraItemPK> kscmtPerCostExtraItemPK;
	
	/** The kscst est aggregate set. */
	public static volatile SingularAttribute<KscmtPerCostExtraItem, KscmtEstAggregate> kscmtEstAggregate;
}
