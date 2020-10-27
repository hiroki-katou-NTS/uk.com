/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.shift.estimate.employment;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KscmtEstDaysEmpPK_.
 */
@StaticMetamodel(KscmtEstDaysEmpPK.class)
public class KscmtEstDaysEmpPK_ {

	/** The cid. */
	public static volatile SingularAttribute<KscmtEstDaysEmpPK, String> cid;
	
	/** The empcd. */
	public static volatile SingularAttribute<KscmtEstDaysEmpPK, String> empcd;
	
	/** The target year. */
	public static volatile SingularAttribute<KscmtEstDaysEmpPK, Integer> targetYear;
	
	/** The target cls. */
	public static volatile SingularAttribute<KscmtEstDaysEmpPK, Integer> targetCls;
	
}