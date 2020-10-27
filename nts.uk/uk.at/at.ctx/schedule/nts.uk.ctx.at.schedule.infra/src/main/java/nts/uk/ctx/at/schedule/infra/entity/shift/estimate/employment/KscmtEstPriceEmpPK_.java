/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.shift.estimate.employment;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KscmtEstPriceEmpPK_.
 */
@StaticMetamodel(KscmtEstPriceEmpPK.class)
public class KscmtEstPriceEmpPK_ {

	/** The cid. */
	public static volatile SingularAttribute<KscmtEstPriceEmpPK, String> cid;
	
	/** The empcd. */
	public static volatile SingularAttribute<KscmtEstPriceEmpPK, String> empcd;
	
	/** The target year. */
	public static volatile SingularAttribute<KscmtEstPriceEmpPK, Integer> targetYear;
	
	/** The target cls. */
	public static volatile SingularAttribute<KscmtEstPriceEmpPK, Integer> targetCls;
		
}