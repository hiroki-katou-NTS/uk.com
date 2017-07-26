/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.shift.pattern;

import java.math.BigDecimal;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KwmmtWorkMonthSetPK_.
 */
@StaticMetamodel(KwmmtWorkMonthSetPK.class)
public class KwmmtWorkMonthSetPK_ {

	/** The cid. */
	public static volatile SingularAttribute<KwmmtWorkMonthSetPK, String> cid;
	
	/** The m pattern cd. */
	public static volatile SingularAttribute<KwmmtWorkMonthSetPK, String> mPatternCd;
	
	/** The ymd K. */
	public static volatile SingularAttribute<KwmmtWorkMonthSetPK, BigDecimal> ymdK;
	
}