
/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.shift.pattern.work;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KwwstWeeklyWorkSet_.
 */
@StaticMetamodel(KwwstWeeklyWorkSetPK.class)
public class KwwstWeeklyWorkSetPK_ {

	/** The cid. */
	public static volatile SingularAttribute<KwwstWeeklyWorkSetPK, String> cid;

	/** The day of week. */
	public static volatile SingularAttribute<KwwstWeeklyWorkSetPK, Integer> dayOfWeek;

}
