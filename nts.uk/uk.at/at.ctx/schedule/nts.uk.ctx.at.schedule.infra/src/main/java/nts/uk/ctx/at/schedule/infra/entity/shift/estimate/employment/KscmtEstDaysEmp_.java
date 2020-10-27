/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.shift.estimate.employment;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KscmtEstDaysEmp_.
 */
@StaticMetamodel(KscmtEstDaysEmp.class)
public class KscmtEstDaysEmp_ {

	/** The kscmt est days emp set PK. */
	public static volatile SingularAttribute<KscmtEstDaysEmp, KscmtEstDaysEmpPK> kscmtEstDaysEmpPK;
	
	/** The est condition 1 st days. */
	public static volatile SingularAttribute<KscmtEstDaysEmp, Integer> estCondition1stDays;
	
	/** The est condition 2 nd days. */
	public static volatile SingularAttribute<KscmtEstDaysEmp, Integer> estCondition2ndDays;
	
	/** The est condition 3 rd days. */
	public static volatile SingularAttribute<KscmtEstDaysEmp, Integer> estCondition3rdDays;
	
	/** The est condition 4 th days. */
	public static volatile SingularAttribute<KscmtEstDaysEmp, Integer> estCondition4thDays;
	
	/** The est condition 5 th days. */
	public static volatile SingularAttribute<KscmtEstDaysEmp, Integer> estCondition5thDays;
	
}