/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.shortworktime;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import nts.arc.time.GeneralDate;

/**
 * The Class KshmtShorttimeHist_.
 */
@StaticMetamodel(KshmtShorttimeHist.class)
public class KshmtShorttimeHist_ {

	/** The bshmt worktime hist PK. */
	public static SingularAttribute<KshmtShorttimeHist, KshmtShorttimeHistPK> kshmtShorttimeHistPK;
	
	/** The str ymd. */
	public static SingularAttribute<KshmtShorttimeHist, GeneralDate> strYmd;
	
	/** The end ymd. */
	public static SingularAttribute<KshmtShorttimeHist, GeneralDate> endYmd;
	 
}
