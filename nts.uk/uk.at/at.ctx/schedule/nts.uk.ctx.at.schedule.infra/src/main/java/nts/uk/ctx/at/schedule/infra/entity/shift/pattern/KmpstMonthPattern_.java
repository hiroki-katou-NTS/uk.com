/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.shift.pattern;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KmpstMonthPattern_.
 */
@StaticMetamodel(KmpstMonthPattern.class)
public class KmpstMonthPattern_ {

	/** The kmpst month pattern PK. */
	public static volatile SingularAttribute<KmpstMonthPattern, KmpstMonthPatternPK> kmpstMonthPatternPK;
	
	/** The pattern name. */
	public static volatile SingularAttribute<KmpstMonthPattern, String> patternName;

}