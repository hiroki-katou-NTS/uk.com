/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.shift.estimate.employment;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KscmtEstPriceEmp_.
 */
@StaticMetamodel(KscmtEstPriceEmp.class)
public class KscmtEstPriceEmp_ {

	/** The kscmt est price Emp set PK. */
	public static volatile SingularAttribute<KscmtEstPriceEmp, KscmtEstPriceEmpPK> kscmtEstPriceEmpPK;
	
	/** The est condition 1 st mny. */
	public static volatile SingularAttribute<KscmtEstPriceEmp, Integer> estCondition1stMny;
	
	/** The est condition 2 nd mny. */
	public static volatile SingularAttribute<KscmtEstTimeEmp, Integer> estCondition2ndMny;
	
	/** The est condition 3 rd mny. */
	public static volatile SingularAttribute<KscmtEstTimeEmp, Integer> estCondition3rdMny;
	
	/** The est condition 4 th mny. */
	public static volatile SingularAttribute<KscmtEstTimeEmp, Integer> estCondition4thMny;
	
	/** The est condition 5 th mny. */
	public static volatile SingularAttribute<KscmtEstTimeEmp, Integer> estCondition5thMny;
	
}