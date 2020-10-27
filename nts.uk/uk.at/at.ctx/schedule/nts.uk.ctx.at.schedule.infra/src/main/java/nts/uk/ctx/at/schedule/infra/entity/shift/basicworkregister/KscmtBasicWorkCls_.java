/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.shift.basicworkregister;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KscmtBasicWorkCls_.
 */
@StaticMetamodel(KscmtBasicWorkCls.class)
public class KscmtBasicWorkCls_ {
	
	/** The kcbmt classify work set PK. */
	public static volatile SingularAttribute<KscmtBasicWorkCls, KscmtBasicWorkClsPK> kscmtBasicWorkClsPK;

	/** The worktype code. */
	public static volatile SingularAttribute<KscmtBasicWorkCls, String> worktypeCode;

	/** The working code. */
	public static volatile SingularAttribute<KscmtBasicWorkCls, String> workingCode;

	
}
