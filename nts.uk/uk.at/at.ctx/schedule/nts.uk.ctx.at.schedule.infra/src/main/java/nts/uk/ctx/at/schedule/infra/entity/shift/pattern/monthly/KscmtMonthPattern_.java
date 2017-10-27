/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.shift.pattern.monthly;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KscmtMonthPattern_.
 */
@StaticMetamodel(KscmtMonthPattern.class)
public class KscmtMonthPattern_ {

	/** The kscmt month pattern PK. */
	public static volatile SingularAttribute<KscmtMonthPattern, KscmtMonthPatternPK> kscmtMonthPatternPK;
	
	/** The pattern name. */
	public static volatile SingularAttribute<KscmtMonthPattern, String> patternName;

}