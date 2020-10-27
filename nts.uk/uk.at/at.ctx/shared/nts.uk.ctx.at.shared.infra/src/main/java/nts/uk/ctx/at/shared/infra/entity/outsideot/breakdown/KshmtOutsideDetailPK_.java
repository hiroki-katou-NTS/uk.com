/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.outsideot.breakdown;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KshmtOutsideDetailPK_.
 */
@StaticMetamodel(KshmtOutsideDetailPK.class)
public class KshmtOutsideDetailPK_ {

	/** The cid. */
	public static volatile SingularAttribute<KshmtOutsideDetailPK, String> cid;
	
	/** The brd item no. */
	public static volatile SingularAttribute<KshmtOutsideDetailPK, Integer> brdItemNo;
	
}
