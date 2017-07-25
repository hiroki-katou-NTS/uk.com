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
@StaticMetamodel(KmpmtMonthPattern.class)
public class KmpmtMonthPattern_ {

	/** The kmpmt month pattern PK. */
	public static volatile SingularAttribute<KmpmtMonthPattern, KmpmtMonthPatternPK> kmpmtMonthPatternPK;
	
	/** The pattern name. */
	public static volatile SingularAttribute<KmpmtMonthPattern, String> patternName;

}