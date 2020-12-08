/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.shift.estimate.employment;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KscmtEstPriceEmpSetPK_.
 */
@StaticMetamodel(KscmtEstPriceEmpSetPK.class)
public class KscmtEstPriceEmpSetPK_ {

	/** The cid. */
	public static volatile SingularAttribute<KscmtEstPriceEmpSetPK, String> cid;
	
	/** The empcd. */
	public static volatile SingularAttribute<KscmtEstPriceEmpSetPK, String> empcd;
	
	/** The target year. */
	public static volatile SingularAttribute<KscmtEstPriceEmpSetPK, Integer> targetYear;
	
	/** The target cls. */
	public static volatile SingularAttribute<KscmtEstPriceEmpSetPK, Integer> targetCls;
		
}