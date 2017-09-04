/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.shift.estimate.employment;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KscmtEstTimeEmpSet_.
 */
@StaticMetamodel(KscmtEstTimeEmpSet.class)
public class KscmtEstTimeEmpSet_ {

	/** The kscmt est time emp set PK. */
	public static volatile SingularAttribute<KscmtEstTimeEmpSet, KscmtEstTimeEmpSetPK> kscmtEstTimeEmpSetPK;
	
	/** The est condition 1 st time. */
	public static volatile SingularAttribute<KscmtEstTimeEmpSet, Integer> estCondition1stTime;
	
	/** The est condition 2 nd time. */
	public static volatile SingularAttribute<KscmtEstTimeEmpSet, Integer> estCondition2ndTime;
	
	/** The est condition 3 rd time. */
	public static volatile SingularAttribute<KscmtEstTimeEmpSet, Integer> estCondition3rdTime;
	
	/** The est condition 4 th time. */
	public static volatile SingularAttribute<KscmtEstTimeEmpSet, Integer> estCondition4thTime;
	
	/** The est condition 5 th time. */
	public static volatile SingularAttribute<KscmtEstTimeEmpSet, Integer> estCondition5thTime;
	
}