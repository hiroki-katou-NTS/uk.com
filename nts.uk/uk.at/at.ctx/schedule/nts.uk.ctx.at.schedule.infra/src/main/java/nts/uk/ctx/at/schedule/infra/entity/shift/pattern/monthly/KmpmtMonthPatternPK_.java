/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.shift.pattern.monthly;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KmpmtMonthPatternPK_.
 */
@StaticMetamodel(KmpmtMonthPatternPK.class)
public class KmpmtMonthPatternPK_ {

	/** The cid. */
	public static volatile SingularAttribute<KmpmtMonthPatternPK, String> cid;
	
	/** The m pattern cd. */
	public static volatile SingularAttribute<KmpmtMonthPatternPK, String> mPatternCd;

}
