/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.shift.estimate.personal;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KscmtEstTimePerSet_.
 */
@StaticMetamodel(KscmtEstTimePerSet.class)
public class KscmtEstTimePerSet_ {

	/** The kscmt est time Per set PK. */
	public static volatile SingularAttribute<KscmtEstTimePerSet, KscmtEstTimePerSetPK> kscmtEstTimePerSetPK;
	
	/** The est condition 1 st time. */
	public static volatile SingularAttribute<KscmtEstTimePerSet, Integer> estCondition1stTime;
	
	/** The est condition 2 nd time. */
	public static volatile SingularAttribute<KscmtEstTimePerSet, Integer> estCondition2ndTime;
	
	/** The est condition 3 rd time. */
	public static volatile SingularAttribute<KscmtEstTimePerSet, Integer> estCondition3rdTime;
	
	/** The est condition 4 th time. */
	public static volatile SingularAttribute<KscmtEstTimePerSet, Integer> estCondition4thTime;
	
	/** The est condition 5 th time. */
	public static volatile SingularAttribute<KscmtEstTimePerSet, Integer> estCondition5thTime;
	
}