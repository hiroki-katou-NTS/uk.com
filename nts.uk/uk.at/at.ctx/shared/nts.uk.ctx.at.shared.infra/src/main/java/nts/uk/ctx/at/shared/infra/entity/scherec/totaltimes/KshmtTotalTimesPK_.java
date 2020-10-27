/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.scherec.totaltimes;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KshmtTotalTimesPK_.
 */
@StaticMetamodel(KshmtTotalTimesPK.class)
public class KshmtTotalTimesPK_ {

	/** The cid. */
	public static volatile SingularAttribute<KshmtTotalTimesPK, String> cid;

	/** The total times no. */
	public static volatile SingularAttribute<KshmtTotalTimesPK, Integer> totalTimesNo;

}
