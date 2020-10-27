/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.shift.estimate.company;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KscmtEstTimeComPK_.
 */
@StaticMetamodel(KscmtEstTimeComPK.class)
public class KscmtEstTimeComPK_ {

	/** The cid. */
	public static volatile SingularAttribute<KscmtEstTimeComPK, String> cid;
	
	/** The target year. */
	public static volatile SingularAttribute<KscmtEstTimeComPK, Integer> targetYear;
	
	/** The target cls. */
	public static volatile SingularAttribute<KscmtEstTimeComPK, Integer> targetCls;

}