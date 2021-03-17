/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.shift.basicworkregister;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KscmtClassifyWorkSet_.
 */
@StaticMetamodel(KscmtBasicWorkCls.class)
public class KscmtClassifyWorkSet_ {
	
	/** The kcbmt classify work set PK. */
	public static volatile SingularAttribute<KscmtBasicWorkCls, KscmtClassifyWorkSetPK> kscmtClassifyWorkSetPK;

	/** The worktype code. */
	public static volatile SingularAttribute<KscmtBasicWorkCls, String> worktypeCode;

	/** The working code. */
	public static volatile SingularAttribute<KscmtBasicWorkCls, String> workingCode;

	
}
