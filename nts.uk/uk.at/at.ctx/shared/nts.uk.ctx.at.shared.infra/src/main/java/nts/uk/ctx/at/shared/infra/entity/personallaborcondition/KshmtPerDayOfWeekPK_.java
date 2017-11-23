/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.personallaborcondition;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import nts.arc.time.GeneralDate;

/**
 * The Class KshmtPerDayOfWeekPK_.
 */
@StaticMetamodel(KshmtPerDayOfWeekPK.class)
public class KshmtPerDayOfWeekPK_ {

	/** The sid. */
	public static volatile SingularAttribute<KshmtPerDayOfWeekPK, String> sid;
	
	/** The day of week atr. */
	public static volatile SingularAttribute<KshmtPerDayOfWeekPK, Integer> dayOfWeekAtr;
	
	/** The start ymd. */
	public static volatile SingularAttribute<KshmtPerDayOfWeekPK, GeneralDate> startYmd;
	
	/** The end ymd. */
	public static volatile SingularAttribute<KshmtPerDayOfWeekPK, GeneralDate> endYmd;
}
