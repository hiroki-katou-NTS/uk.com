/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.shift.pattern;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KwmstWorkMonthSetPK_.
 */
@StaticMetamodel(KwmstWorkMonthSetPK.class)
public class KwmstWorkMonthSetPK_ {

	/** The cid. */
	public static volatile SingularAttribute<KwmstWorkMonthSetPK, String> cid;
	
	/** The work type cd. */
	public static volatile SingularAttribute<KwmstWorkMonthSetPK, String> workTypeCd;
	
	/** The working cd. */
	public static volatile SingularAttribute<KwmstWorkMonthSetPK, String> workingCd;
	
}