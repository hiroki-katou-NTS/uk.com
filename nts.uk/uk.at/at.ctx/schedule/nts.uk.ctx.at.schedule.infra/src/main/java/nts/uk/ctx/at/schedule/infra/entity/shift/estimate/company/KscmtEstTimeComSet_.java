/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.shift.estimate.company;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KscmtEstTimeComSet_.
 */
@StaticMetamodel(KscmtEstTimeComSet.class)
public class KscmtEstTimeComSet_ {

	/** The kscmt est time com set PK. */
	public static volatile SingularAttribute<KscmtEstTimeComSet, KscmtEstTimeComSetPK> kscmtEstTimeComSetPK;
	
	/** The est condition 1 st time. */
	public static volatile SingularAttribute<KscmtEstTimeComSet, Integer> estCondition1stTime;
	
	/** The est condition 2 nd time. */
	public static volatile SingularAttribute<KscmtEstTimeComSet, Integer> estCondition2ndTime;
	
	/** The est condition 3 rd time. */
	public static volatile SingularAttribute<KscmtEstTimeComSet, Integer> estCondition3rdTime;
	
	/** The est condition 4 th time. */
	public static volatile SingularAttribute<KscmtEstTimeComSet, Integer> estCondition4thTime;
	
	/** The est condition 5 th time. */
	public static volatile SingularAttribute<KscmtEstTimeComSet, Integer> estCondition5thTime;
	
}