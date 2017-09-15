/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.outsideot.breakdown;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KshstOverTimeBrdPK_.
 */
@StaticMetamodel(KshstOverTimeBrdPK.class)
public class KshstOverTimeBrdPK_ {

	/** The cid. */
	public static volatile SingularAttribute<KshstOverTimeBrdPK, String> cid;
	
	/** The brd item no. */
	public static volatile SingularAttribute<KshstOverTimeBrdPK, Integer> brdItemNo;
	
}
