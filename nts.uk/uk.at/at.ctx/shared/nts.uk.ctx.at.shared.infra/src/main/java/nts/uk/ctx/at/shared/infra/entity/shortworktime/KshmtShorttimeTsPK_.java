/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.shortworktime;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KshmtShorttimeTsPK_.
 */
@StaticMetamodel(KshmtShorttimeTsPK.class)
public class KshmtShorttimeTsPK_ {

	/** The sid. */
	public static SingularAttribute<KshmtShorttimeTsPK, String> sid;
	
	/** The hist id. */
	public static SingularAttribute<KshmtShorttimeTsPK, String> histId;
	
	/** The str clock. */
	public static SingularAttribute<KshmtShorttimeTsPK, Integer> strClock;
}
