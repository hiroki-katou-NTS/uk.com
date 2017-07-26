/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.shift.basicworkregister;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KcbmtClassifyWorkSetPK_.
 */
@StaticMetamodel(KcbmtClassifyWorkSetPK.class)
public class KcbmtClassifyWorkSetPK_ {

	/** The cid. */
	public static volatile SingularAttribute<KcbmtClassifyWorkSetPK, String> cid;

	/** The classify code. */
	public static volatile SingularAttribute<KcbmtClassifyWorkSetPK, String> classifyCode;
	
	/** The workday division. */
	public static volatile SingularAttribute<KcbmtClassifyWorkSetPK, Integer> workdayDivision;
}
