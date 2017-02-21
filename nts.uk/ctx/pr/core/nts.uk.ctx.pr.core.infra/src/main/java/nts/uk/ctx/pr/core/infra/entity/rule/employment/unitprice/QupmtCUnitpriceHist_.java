/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.entity.rule.employment.unitprice;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class QupmtCUnitpriceHist_.
 */
@StaticMetamodel(QupmtCUnitpriceHist.class)
public class QupmtCUnitpriceHist_ {

	/** The qupmt C unitprice hist PK. */
	public static volatile SingularAttribute<QupmtCUnitpriceHist, QupmtCUnitpriceHistPK> qupmtCUnitpriceHistPK;

	/** The ins date. */
	public static volatile SingularAttribute<QupmtCUnitpriceHist, Date> insDate;

	/** The ins ccd. */
	public static volatile SingularAttribute<QupmtCUnitpriceHist, String> insCcd;

	/** The ins scd. */
	public static volatile SingularAttribute<QupmtCUnitpriceHist, String> insScd;

	/** The ins pg. */
	public static volatile SingularAttribute<QupmtCUnitpriceHist, String> insPg;

	/** The upd date. */
	public static volatile SingularAttribute<QupmtCUnitpriceHist, Date> updDate;

	/** The upd ccd. */
	public static volatile SingularAttribute<QupmtCUnitpriceHist, String> updCcd;

	/** The upd scd. */
	public static volatile SingularAttribute<QupmtCUnitpriceHist, String> updScd;

	/** The upd pg. */
	public static volatile SingularAttribute<QupmtCUnitpriceHist, String> updPg;

	/** The exclus ver. */
	public static volatile SingularAttribute<QupmtCUnitpriceHist, Long> exclusVer;

	/** The str ym. */
	public static volatile SingularAttribute<QupmtCUnitpriceHist, Integer> strYm;

	/** The end ym. */
	public static volatile SingularAttribute<QupmtCUnitpriceHist, Integer> endYm;

	/** The budget. */
	public static volatile SingularAttribute<QupmtCUnitpriceHist, BigDecimal> budget;

	/** The fix pay set. */
	public static volatile SingularAttribute<QupmtCUnitpriceHist, Integer> fixPaySet;

	/** The fix pay atr. */
	public static volatile SingularAttribute<QupmtCUnitpriceHist, Integer> fixPayAtr;

	/** The fix pay atr monthly. */
	public static volatile SingularAttribute<QupmtCUnitpriceHist, Integer> fixPayAtrMonthly;

	/** The fix pay atr daymonth. */
	public static volatile SingularAttribute<QupmtCUnitpriceHist, Integer> fixPayAtrDaymonth;

	/** The fix pay atr daily. */
	public static volatile SingularAttribute<QupmtCUnitpriceHist, Integer> fixPayAtrDaily;

	/** The fix pay atr hourly. */
	public static volatile SingularAttribute<QupmtCUnitpriceHist, Integer> fixPayAtrHourly;

	/** The memo. */
	public static volatile SingularAttribute<QupmtCUnitpriceHist, String> memo;

	/** The qupmt C unitprice head. */
	public static volatile SingularAttribute<QupmtCUnitpriceHist, QupmtCUnitpriceHead> qupmtCUnitpriceHead;
}