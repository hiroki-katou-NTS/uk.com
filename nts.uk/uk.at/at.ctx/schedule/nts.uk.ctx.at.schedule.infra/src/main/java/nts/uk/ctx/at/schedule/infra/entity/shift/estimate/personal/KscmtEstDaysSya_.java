/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.shift.estimate.personal;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KscmtEstDaysSya_.
 */
@StaticMetamodel(KscmtEstDaysSya.class)
public class KscmtEstDaysSya_ {

	/** The kscmt est days Per set PK. */
	public static volatile SingularAttribute<KscmtEstDaysSya, KscmtEstDaysSyaPK> kscmtEstDaysSyaPK;
	
	/** The est condition 1 st days. */
	public static volatile SingularAttribute<KscmtEstDaysSya, Integer> estCondition1stDays;
	
	/** The est condition 2 nd days. */
	public static volatile SingularAttribute<KscmtEstDaysSya, Integer> estCondition2ndDays;
	
	/** The est condition 3 rd days. */
	public static volatile SingularAttribute<KscmtEstDaysSya, Integer> estCondition3rdDays;
	
	/** The est condition 4 th days. */
	public static volatile SingularAttribute<KscmtEstDaysSya, Integer> estCondition4thDays;
	
	/** The est condition 5 th days. */
	public static volatile SingularAttribute<KscmtEstDaysSya, Integer> estCondition5thDays;
	
}