
/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.shift.pattern.work;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KscmtWeeklyWorkSetPK_.
 */
@StaticMetamodel(KscmtWeeklyWorkSetPK.class)
public class KscmtWeeklyWorkSetPK_ {

	/** The cid. */
	public static volatile SingularAttribute<KscmtWeeklyWorkSetPK, String> cid;

	/** The day of week. */
	public static volatile SingularAttribute<KscmtWeeklyWorkSetPK, Integer> dayOfWeek;

}
