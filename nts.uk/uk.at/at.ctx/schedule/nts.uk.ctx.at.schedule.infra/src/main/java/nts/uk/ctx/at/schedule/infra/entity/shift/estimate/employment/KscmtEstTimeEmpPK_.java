/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.shift.estimate.employment;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KscmtEstTimeEmpPK_.
 */
@StaticMetamodel(KscmtEstTimeEmpPK.class)
public class KscmtEstTimeEmpPK_ {

	/** The cid. */
	public static volatile SingularAttribute<KscmtEstTimeEmpPK, String> cid;
	
	/** The empcd. */
	public static volatile SingularAttribute<KscmtEstTimeEmpPK, String> empcd;
	
	/** The target year. */
	public static volatile SingularAttribute<KscmtEstTimeEmpPK, Integer> targetYear;
	
	/** The target cls. */
	public static volatile SingularAttribute<KscmtEstTimeEmpPK, Integer> targetCls;

}