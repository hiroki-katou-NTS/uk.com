/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.shift.estimate.employment;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KscmtEstTimeEmpSetPK_.
 */
@StaticMetamodel(KscmtEstTimeEmpSetPK.class)
public class KscmtEstTimeEmpSetPK_ {

	/** The cid. */
	public static volatile SingularAttribute<KscmtEstTimeEmpSetPK, String> cid;
	
	/** The empcd. */
	public static volatile SingularAttribute<KscmtEstTimeEmpSetPK, String> empcd;
	
	/** The target year. */
	public static volatile SingularAttribute<KscmtEstTimeEmpSetPK, Integer> targetYear;
	
	/** The target cls. */
	public static volatile SingularAttribute<KscmtEstTimeEmpSetPK, Integer> targetCls;

}