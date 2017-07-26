/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.shift.basicworkregister;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KcbmtClassifyWorkSet_.
 */
@StaticMetamodel(KcbmtClassifyWorkSet.class)
public class KcbmtClassifyWorkSet_ {
	
	/** The kcbmt classify work set PK. */
	public static volatile SingularAttribute<KcbmtClassifyWorkSet, KcbmtClassifyWorkSetPK> kcbmtClassifyWorkSetPK;

	/** The worktype code. */
	public static volatile SingularAttribute<KcbmtClassifyWorkSet, String> worktypeCode;

	/** The working code. */
	public static volatile SingularAttribute<KcbmtClassifyWorkSet, String> workingCode;

	
}
