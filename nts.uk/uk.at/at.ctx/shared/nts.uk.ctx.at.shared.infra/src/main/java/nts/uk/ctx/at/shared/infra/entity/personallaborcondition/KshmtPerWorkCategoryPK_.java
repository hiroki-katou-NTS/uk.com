/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.personallaborcondition;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import nts.arc.time.GeneralDate;

/**
 * The Class KshmtPerWorkCategoryPK_.
 */
@StaticMetamodel(KshmtPerWorkCategoryPK.class)
public class KshmtPerWorkCategoryPK_ {

	/** The sid. */
	public static volatile SingularAttribute<KshmtPerWorkCategoryPK, String> sid;
	
	/** The work category atr. */
	public static volatile SingularAttribute<KshmtPerWorkCategoryPK, Integer> workCategoryAtr;
	
	/** The start ymd. */
	public static volatile SingularAttribute<KshmtPerWorkCategoryPK, GeneralDate> startYmd;
	
	/** The end ymd. */
	public static volatile SingularAttribute<KshmtPerWorkCategoryPK, GeneralDate> endYmd;
}
