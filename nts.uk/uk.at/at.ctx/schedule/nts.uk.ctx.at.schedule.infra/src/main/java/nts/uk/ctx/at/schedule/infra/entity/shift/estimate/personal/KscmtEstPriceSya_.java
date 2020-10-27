/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.shift.estimate.personal;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KscmtEstPriceSya_.
 */
@StaticMetamodel(KscmtEstPriceSya.class)
public class KscmtEstPriceSya_ {

	/** The kscmt est price Per set PK. */
	public static volatile SingularAttribute<KscmtEstPriceSya, KscmtEstPriceSyaPK> kscmtEstPriceSyaPK;
	
	/** The est condition 1 st mny. */
	public static volatile SingularAttribute<KscmtEstPriceSya, Integer> estCondition1stMny;
	
	/** The est condition 2 nd mny. */
	public static volatile SingularAttribute<KscmtEstTimeSya, Integer> estCondition2ndMny;
	
	/** The est condition 3 rd mny. */
	public static volatile SingularAttribute<KscmtEstTimeSya, Integer> estCondition3rdMny;
	
	/** The est condition 4 th mny. */
	public static volatile SingularAttribute<KscmtEstTimeSya, Integer> estCondition4thMny;
	
	/** The est condition 5 th mny. */
	public static volatile SingularAttribute<KscmtEstTimeSya, Integer> estCondition5thMny;
	
}