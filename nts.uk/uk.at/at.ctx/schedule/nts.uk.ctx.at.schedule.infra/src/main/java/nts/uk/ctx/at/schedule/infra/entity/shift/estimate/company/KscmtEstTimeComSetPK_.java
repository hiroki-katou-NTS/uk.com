/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.shift.estimate.company;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KscmtEstTimeComSetPK_.
 */
@StaticMetamodel(KscmtEstTimeComSetPK.class)
public class KscmtEstTimeComSetPK_ {

	/** The cid. */
	public static volatile SingularAttribute<KscmtEstTimeComSetPK, String> cid;
	
	/** The target year. */
	public static volatile SingularAttribute<KscmtEstTimeComSetPK, Integer> targetYear;
	
	/** The target cls. */
	public static volatile SingularAttribute<KscmtEstTimeComSetPK, Integer> targetCls;

}