/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.shift.pattern;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KwmmtWorkMonthSet_.
 */
@StaticMetamodel(KwmmtWorkMonthSet.class)
public class KwmmtWorkMonthSet_ {

	/** The kwmmt work month set PK. */
	public static volatile SingularAttribute<KwmmtWorkMonthSet, KwmmtWorkMonthSetPK> kwmmtWorkMonthSetPK;
	
	/** The work type cd. */
	public static volatile SingularAttribute<KwmmtWorkMonthSet, String> workTypeCd;
	
	/** The working cd. */
	public static volatile SingularAttribute<KwmmtWorkMonthSet, String> workingCd;
	
}