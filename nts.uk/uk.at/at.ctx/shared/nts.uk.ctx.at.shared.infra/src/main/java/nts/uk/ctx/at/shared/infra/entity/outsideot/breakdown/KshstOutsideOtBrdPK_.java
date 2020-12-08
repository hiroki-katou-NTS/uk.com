/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.outsideot.breakdown;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KshstOutsideOtBrdPK_.
 */
@StaticMetamodel(KshstOutsideOtBrdPK.class)
public class KshstOutsideOtBrdPK_ {

	/** The cid. */
	public static volatile SingularAttribute<KshstOutsideOtBrdPK, String> cid;
	
	/** The brd item no. */
	public static volatile SingularAttribute<KshstOutsideOtBrdPK, Integer> brdItemNo;
	
}
