/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.shift.basicworkregister;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KscmtBasicWorkClsPK_.
 */
@StaticMetamodel(KscmtBasicWorkClsPK.class)
public class KscmtBasicWorkClsPK_ {

	/** The cid. */
	public static volatile SingularAttribute<KscmtBasicWorkClsPK, String> cid;

	/** The classify code. */
	public static volatile SingularAttribute<KscmtBasicWorkClsPK, String> classifyCode;
	
	/** The workday division. */
	public static volatile SingularAttribute<KscmtBasicWorkClsPK, Integer> workdayDivision;
}
