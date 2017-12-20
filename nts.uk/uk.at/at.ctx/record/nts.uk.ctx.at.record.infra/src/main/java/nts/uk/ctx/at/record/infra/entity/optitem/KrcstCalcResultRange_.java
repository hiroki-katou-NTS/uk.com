/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.entity.optitem;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KrcstCalcResultRange_.
 */
@StaticMetamodel(KrcstCalcResultRange.class)
public class KrcstCalcResultRange_ {

	/** The krcst calc result range PK. */
	public static volatile SingularAttribute<KrcstCalcResultRange, KrcstCalcResultRangePK> krcstCalcResultRangePK;

	/** The emp appl atr. */
	public static volatile SingularAttribute<KrcstCalcResultRange, Integer> empApplAtr;

	/** The upper limit atr. */
	public static volatile SingularAttribute<KrcstCalcResultRange, Integer> upperLimitAtr;

	/** The lower limit atr. */
	public static volatile SingularAttribute<KrcstCalcResultRange, Integer> lowerLimitAtr;

	/** The upper time range. */
	public static volatile SingularAttribute<KrcstCalcResultRange, Integer> upperTimeRange;

	/** The lower time range. */
	public static volatile SingularAttribute<KrcstCalcResultRange, Integer> lowerTimeRange;

	/** The upper number range. */
	public static volatile SingularAttribute<KrcstCalcResultRange, Double> upperNumberRange;

	/** The lower number range. */
	public static volatile SingularAttribute<KrcstCalcResultRange, Double> lowerNumberRange;

	/** The upper amount range. */
	public static volatile SingularAttribute<KrcstCalcResultRange, Integer> upperAmountRange;

	/** The lower amount range. */
	public static volatile SingularAttribute<KrcstCalcResultRange, Integer> lowerAmountRange;

}
