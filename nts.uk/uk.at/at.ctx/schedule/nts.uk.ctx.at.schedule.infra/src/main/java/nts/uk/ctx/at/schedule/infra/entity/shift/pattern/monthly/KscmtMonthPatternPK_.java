/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.shift.pattern.monthly;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KscmtMonthPatternPK_.
 */
@StaticMetamodel(KscmtMonthPatternPK.class)
public class KscmtMonthPatternPK_ {

	/** The cid. */
	public static volatile SingularAttribute<KscmtMonthPatternPK, String> cid;
	
	/** The m pattern cd. */
	public static volatile SingularAttribute<KscmtMonthPatternPK, String> mPatternCd;

}
