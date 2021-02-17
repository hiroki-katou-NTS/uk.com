/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.shortworktime;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import nts.arc.time.GeneralDate;

/**
 * The Class BshmtWorktimeHist_.
 */
@StaticMetamodel(KshmtShorttimeHist.class)
public class BshmtWorktimeHist_ {

	/** The bshmt worktime hist PK. */
	public static SingularAttribute<KshmtShorttimeHist, BshmtWorktimeHistPK> bshmtWorktimeHistPK;
	
	/** The str ymd. */
	public static SingularAttribute<KshmtShorttimeHist, GeneralDate> strYmd;
	
	/** The end ymd. */
	public static SingularAttribute<KshmtShorttimeHist, GeneralDate> endYmd;
	 
}
