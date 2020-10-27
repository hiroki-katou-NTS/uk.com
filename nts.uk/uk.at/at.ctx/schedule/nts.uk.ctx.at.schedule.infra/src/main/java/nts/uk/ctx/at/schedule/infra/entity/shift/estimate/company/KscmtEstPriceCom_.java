/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.shift.estimate.company;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KscmtEstPriceCom_.
 */
@StaticMetamodel(KscmtEstPriceCom.class)
public class KscmtEstPriceCom_ {

	/** The kscmt est price com set PK. */
	public static volatile SingularAttribute<KscmtEstPriceCom, KscmtEstPriceComPK> kscmtEstPriceComPK;
	
	/** The est condition 1 st mny. */
	public static volatile SingularAttribute<KscmtEstPriceCom, Integer> estCondition1stMny;
	
	/** The est condition 2 nd mny. */
	public static volatile SingularAttribute<KscmtEstTimeCom, Integer> estCondition2ndMny;
	
	/** The est condition 3 rd mny. */
	public static volatile SingularAttribute<KscmtEstTimeCom, Integer> estCondition3rdMny;
	
	/** The est condition 4 th mny. */
	public static volatile SingularAttribute<KscmtEstTimeCom, Integer> estCondition4thMny;
	
	/** The est condition 5 th mny. */
	public static volatile SingularAttribute<KscmtEstTimeCom, Integer> estCondition5thMny;
	
}