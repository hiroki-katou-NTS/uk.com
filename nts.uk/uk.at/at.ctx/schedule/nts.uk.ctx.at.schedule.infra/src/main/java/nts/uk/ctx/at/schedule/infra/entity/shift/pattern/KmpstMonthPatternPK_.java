/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.shift.pattern;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KmpstMonthPatternPK_.
 */
@StaticMetamodel(KmpstMonthPatternPK.class)
public class KmpstMonthPatternPK_ {

	/** The cid. */
	public static volatile SingularAttribute<KmpstMonthPatternPK, String> cid;
	
	/** The pattern cd. */
	public static volatile SingularAttribute<KmpstMonthPatternPK, String> patternCd;

}