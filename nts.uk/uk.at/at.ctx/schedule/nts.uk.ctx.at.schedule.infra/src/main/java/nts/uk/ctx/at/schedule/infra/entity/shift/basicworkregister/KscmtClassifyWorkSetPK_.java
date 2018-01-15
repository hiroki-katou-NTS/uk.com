/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.shift.basicworkregister;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KscmtClassifyWorkSetPK_.
 */
@StaticMetamodel(KscmtClassifyWorkSetPK.class)
public class KscmtClassifyWorkSetPK_ {

	/** The cid. */
	public static volatile SingularAttribute<KscmtClassifyWorkSetPK, String> cid;

	/** The classify code. */
	public static volatile SingularAttribute<KscmtClassifyWorkSetPK, String> classifyCode;
	
	/** The workday division. */
	public static volatile SingularAttribute<KscmtClassifyWorkSetPK, Integer> workdayDivision;
}
