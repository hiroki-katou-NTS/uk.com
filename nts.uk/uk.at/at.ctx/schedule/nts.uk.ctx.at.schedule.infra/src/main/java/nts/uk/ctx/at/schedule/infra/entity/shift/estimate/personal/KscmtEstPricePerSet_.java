/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.shift.estimate.personal;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KscmtEstPricePerSet_.
 */
@StaticMetamodel(KscmtEstPricePerSet.class)
public class KscmtEstPricePerSet_ {

	/** The kscmt est price Per set PK. */
	public static volatile SingularAttribute<KscmtEstPricePerSet, KscmtEstPricePerSetPK> kscmtEstPricePerSetPK;
	
	/** The est condition 1 st mny. */
	public static volatile SingularAttribute<KscmtEstPricePerSet, Integer> estCondition1stMny;
	
	/** The est condition 2 nd mny. */
	public static volatile SingularAttribute<KscmtEstTimePerSet, Integer> estCondition2ndMny;
	
	/** The est condition 3 rd mny. */
	public static volatile SingularAttribute<KscmtEstTimePerSet, Integer> estCondition3rdMny;
	
	/** The est condition 4 th mny. */
	public static volatile SingularAttribute<KscmtEstTimePerSet, Integer> estCondition4thMny;
	
	/** The est condition 5 th mny. */
	public static volatile SingularAttribute<KscmtEstTimePerSet, Integer> estCondition5thMny;
	
}