
/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.shift.pattern;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KwwstWeeklyWorkSet_.
 */
@StaticMetamodel(KwwstWeeklyWorkSet.class)
public class KwwstWeeklyWorkSet_ {

	/** The kwwst weekly work set PK. */
	public static volatile SingularAttribute<KwwstWeeklyWorkSet, KwwstWeeklyWorkSetPK> kwwstWeeklyWorkSetPK;

	/** The work day div. */
	public static volatile SingularAttribute<KwwstWeeklyWorkSet, Integer> workDayDiv;

}
