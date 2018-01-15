
/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.shift.pattern.work;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KscmtWeeklyWorkSet_.
 */
@StaticMetamodel(KscmtWeeklyWorkSet.class)
public class KscmtWeeklyWorkSet_ {

	/** The kscmt weekly work set PK. */
	public static volatile SingularAttribute<KscmtWeeklyWorkSet, KscmtWeeklyWorkSetPK> kscmtWeeklyWorkSetPK;

	/** The work day div. */
	public static volatile SingularAttribute<KscmtWeeklyWorkSet, Integer> workDayDiv;

}
