/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.shift.estimate.company;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KscmtEstPriceComPK_.
 */
@StaticMetamodel(KscmtEstPriceComPK.class)
public class KscmtEstPriceComPK_ {

	/** The cid. */
	public static volatile SingularAttribute<KscmtEstPriceComPK, String> cid;
	
	/** The target year. */
	public static volatile SingularAttribute<KscmtEstPriceComPK, Integer> targetYear;
	
	/** The target cls. */
	public static volatile SingularAttribute<KscmtEstPriceComPK, Integer> targetCls;
		
}