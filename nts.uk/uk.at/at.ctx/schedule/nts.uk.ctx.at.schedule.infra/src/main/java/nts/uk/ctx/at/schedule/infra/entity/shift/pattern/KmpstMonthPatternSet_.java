/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.shift.pattern;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KmpstMonthPatternSet_.
 */
@StaticMetamodel(KmpstMonthPatternSet.class)
public class KmpstMonthPatternSet_ {

	/** The sid. */
	public static volatile SingularAttribute<KmpstMonthPatternSet, String> sid;
	
	/** The m pattern cd. */
	public static volatile SingularAttribute<KmpstMonthPatternSet, String> mPatternCd;
	
}