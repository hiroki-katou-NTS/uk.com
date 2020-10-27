/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.entity.optitem;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KrcmtAnyfResultRange_.
 */
@StaticMetamodel(KrcmtAnyfResultRange.class)
public class KrcmtAnyfResultRange_ {

	/** The krcst calc result range PK. */
	public static volatile SingularAttribute<KrcmtAnyfResultRange, KrcmtAnyfResultRangePK> krcmtAnyfResultRangePK;

	/** The emp appl atr. */
	public static volatile SingularAttribute<KrcmtAnyfResultRange, Integer> empApplAtr;

	/** The upper limit atr. */
	public static volatile SingularAttribute<KrcmtAnyfResultRange, Integer> upperLimitAtr;

	/** The lower limit atr. */
	public static volatile SingularAttribute<KrcmtAnyfResultRange, Integer> lowerLimitAtr;

	/** The upper time range. */
	public static volatile SingularAttribute<KrcmtAnyfResultRange, Integer> upperTimeRange;

	/** The lower time range. */
	public static volatile SingularAttribute<KrcmtAnyfResultRange, Integer> lowerTimeRange;

	/** The upper number range. */
	public static volatile SingularAttribute<KrcmtAnyfResultRange, Double> upperNumberRange;

	/** The lower number range. */
	public static volatile SingularAttribute<KrcmtAnyfResultRange, Double> lowerNumberRange;

	/** The upper amount range. */
	public static volatile SingularAttribute<KrcmtAnyfResultRange, Integer> upperAmountRange;

	/** The lower amount range. */
	public static volatile SingularAttribute<KrcmtAnyfResultRange, Integer> lowerAmountRange;

}
