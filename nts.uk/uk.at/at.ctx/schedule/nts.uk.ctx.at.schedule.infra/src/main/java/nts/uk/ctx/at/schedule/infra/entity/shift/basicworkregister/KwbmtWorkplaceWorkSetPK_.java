/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.shift.basicworkregister;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KwbmtWorkplaceWorkSetPK_.
 */
@StaticMetamodel(KwbmtWorkplaceWorkSetPK.class)
public class KwbmtWorkplaceWorkSetPK_ {

	/** The workplace id. */
	public static volatile SingularAttribute<KwbmtWorkplaceWorkSetPK, String> workplaceId;

	/** The worktype code. */
	public static volatile SingularAttribute<KwbmtWorkplaceWorkSetPK, Integer> workdayDivision;
}
