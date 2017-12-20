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
@StaticMetamodel(BshmtWorktimeHist.class)
public class BshmtWorktimeHist_ {

	/** The bshmt worktime hist PK. */
	public static SingularAttribute<BshmtWorktimeHist, BshmtWorktimeHistPK> bshmtWorktimeHistPK;
	
	/** The str ymd. */
	public static SingularAttribute<BshmtWorktimeHist, GeneralDate> strYmd;
	
	/** The end ymd. */
	public static SingularAttribute<BshmtWorktimeHist, GeneralDate> endYmd;
	 
}
