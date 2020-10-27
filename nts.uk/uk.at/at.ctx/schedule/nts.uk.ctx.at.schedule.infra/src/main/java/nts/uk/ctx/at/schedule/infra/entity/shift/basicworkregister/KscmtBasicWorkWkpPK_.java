/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.shift.basicworkregister;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KscmtBasicWorkWkpPK_.
 */
@StaticMetamodel(KscmtBasicWorkWkpPK.class)
public class KscmtBasicWorkWkpPK_ {

	/** The workplace id. */
	public static volatile SingularAttribute<KscmtBasicWorkWkpPK, String> workplaceId;

	/** The worktype code. */
	public static volatile SingularAttribute<KscmtBasicWorkWkpPK, Integer> workdayDivision;
}
