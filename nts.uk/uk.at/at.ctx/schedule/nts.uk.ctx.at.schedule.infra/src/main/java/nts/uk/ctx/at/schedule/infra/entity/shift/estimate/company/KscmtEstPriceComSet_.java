/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.shift.estimate.company;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KscmtEstPriceComSet_.
 */
@StaticMetamodel(KscmtEstPriceComSet.class)
public class KscmtEstPriceComSet_ {

	/** The kscmt est price com set PK. */
	public static volatile SingularAttribute<KscmtEstPriceComSet, KscmtEstPriceComSetPK> kscmtEstPriceComSetPK;
	
	/** The est condition 1 st mny. */
	public static volatile SingularAttribute<KscmtEstPriceComSet, Integer> estCondition1stMny;
	
	/** The est condition 2 nd mny. */
	public static volatile SingularAttribute<KscmtEstTimeComSet, Integer> estCondition2ndMny;
	
	/** The est condition 3 rd mny. */
	public static volatile SingularAttribute<KscmtEstTimeComSet, Integer> estCondition3rdMny;
	
	/** The est condition 4 th mny. */
	public static volatile SingularAttribute<KscmtEstTimeComSet, Integer> estCondition4thMny;
	
	/** The est condition 5 th mny. */
	public static volatile SingularAttribute<KscmtEstTimeComSet, Integer> estCondition5thMny;
	
}