/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.shift.pattern;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KmpstMonthPatternSetPK_.
 */
@StaticMetamodel(KmpstMonthPatternSetPK.class)
public class KmpstMonthPatternSetPK_ {

	/** The month pattern cd. */
	public static volatile SingularAttribute<KmpstMonthPatternSetPK, String> monthPatternCd;
	
	/** The sid. */
	public static volatile SingularAttribute<KmpstMonthPatternSetPK, String> sid;
	
}