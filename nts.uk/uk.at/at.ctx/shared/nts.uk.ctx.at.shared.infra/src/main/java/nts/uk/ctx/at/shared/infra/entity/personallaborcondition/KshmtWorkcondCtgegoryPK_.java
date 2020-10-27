/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.personallaborcondition;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import nts.arc.time.GeneralDate;

/**
 * The Class KshmtWorkcondCtgegoryPK_.
 */
@StaticMetamodel(KshmtWorkcondCtgegoryPK.class)
public class KshmtWorkcondCtgegoryPK_ {

	/** The sid. */
	public static volatile SingularAttribute<KshmtWorkcondCtgegoryPK, String> sid;
	
	/** The work category atr. */
	public static volatile SingularAttribute<KshmtWorkcondCtgegoryPK, Integer> workCategoryAtr;
	
	/** The start ymd. */
	public static volatile SingularAttribute<KshmtWorkcondCtgegoryPK, GeneralDate> startYmd;
	
	/** The end ymd. */
	public static volatile SingularAttribute<KshmtWorkcondCtgegoryPK, GeneralDate> endYmd;
}
