/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.shift.estimate.employment;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KscmtEstDaysEmpSetPK_.
 */
@StaticMetamodel(KscmtEstDaysEmpSetPK.class)
public class KscmtEstDaysEmpSetPK_ {

	/** The cid. */
	public static volatile SingularAttribute<KscmtEstDaysEmpSetPK, String> cid;
	
	/** The empcd. */
	public static volatile SingularAttribute<KscmtEstDaysEmpSetPK, String> empcd;
	
	/** The target year. */
	public static volatile SingularAttribute<KscmtEstDaysEmpSetPK, Integer> targetYear;
	
	/** The target cls. */
	public static volatile SingularAttribute<KscmtEstDaysEmpSetPK, Integer> targetCls;
	
}