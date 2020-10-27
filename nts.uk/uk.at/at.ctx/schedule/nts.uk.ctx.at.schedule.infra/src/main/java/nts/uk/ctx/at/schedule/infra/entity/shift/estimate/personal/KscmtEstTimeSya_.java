/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.shift.estimate.personal;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KscmtEstTimeSya_.
 */
@StaticMetamodel(KscmtEstTimeSya.class)
public class KscmtEstTimeSya_ {

	/** The kscmt est time Per set PK. */
	public static volatile SingularAttribute<KscmtEstTimeSya, KscmtEstTimeSyaPK> kscmtEstTimeSyaPK;
	
	/** The est condition 1 st time. */
	public static volatile SingularAttribute<KscmtEstTimeSya, Integer> estCondition1stTime;
	
	/** The est condition 2 nd time. */
	public static volatile SingularAttribute<KscmtEstTimeSya, Integer> estCondition2ndTime;
	
	/** The est condition 3 rd time. */
	public static volatile SingularAttribute<KscmtEstTimeSya, Integer> estCondition3rdTime;
	
	/** The est condition 4 th time. */
	public static volatile SingularAttribute<KscmtEstTimeSya, Integer> estCondition4thTime;
	
	/** The est condition 5 th time. */
	public static volatile SingularAttribute<KscmtEstTimeSya, Integer> estCondition5thTime;
	
}