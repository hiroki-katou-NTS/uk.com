/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.shift.pattern.work;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import nts.arc.time.GeneralDate;

/**
 * The Class KscmtWorkMonthSetPK_.
 */
@StaticMetamodel(KscmtWorkMonthSetPK.class)
public class KscmtWorkMonthSetPK_ {

	/** The cid. */
	public static volatile SingularAttribute<KscmtWorkMonthSetPK, String> cid;
	
	/** The m pattern cd. */
	public static volatile SingularAttribute<KscmtWorkMonthSetPK, String> mPatternCd;
	
	/** The ymd K. */
	public static volatile SingularAttribute<KscmtWorkMonthSetPK, GeneralDate> ymdM;
	
}