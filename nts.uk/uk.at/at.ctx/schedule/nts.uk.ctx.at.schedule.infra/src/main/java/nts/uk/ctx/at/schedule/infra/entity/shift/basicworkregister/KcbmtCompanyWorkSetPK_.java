/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.shift.basicworkregister;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KcbmtCompanyWorkSetPK_.
 */
@StaticMetamodel(KcbmtCompanyWorkSetPK.class)
public class KcbmtCompanyWorkSetPK_ {

	/** The cid. */
	public static volatile SingularAttribute<KcbmtCompanyWorkSetPK, String> cid;

	/** The worktype code. */
	public static volatile SingularAttribute<KcbmtCompanyWorkSetPK, Integer> workdayDivision;
}
