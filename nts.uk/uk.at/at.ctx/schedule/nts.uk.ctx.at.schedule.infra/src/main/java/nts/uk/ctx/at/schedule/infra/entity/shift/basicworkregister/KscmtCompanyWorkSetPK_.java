/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.shift.basicworkregister;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KscmtCompanyWorkSetPK_.
 */
@StaticMetamodel(KscmtCompanyWorkSetPK.class)
public class KscmtCompanyWorkSetPK_ {

	/** The cid. */
	public static volatile SingularAttribute<KscmtCompanyWorkSetPK, String> cid;

	/** The worktype code. */
	public static volatile SingularAttribute<KscmtCompanyWorkSetPK, Integer> workdayDivision;
}
