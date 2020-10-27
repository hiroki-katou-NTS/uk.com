/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.entity.optitem.calculation;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KrcmtAnyfRound_.
 */
@StaticMetamodel(KrcmtAnyfRound.class)
public class KrcmtAnyfRound_ {

	/** The krcmt formula rounding PK. */
	public static volatile SingularAttribute<KrcmtAnyfRound, KrcmtAnyfRoundPK> krcmtAnyfRoundPK;

	/** The number rounding. */
	public static volatile SingularAttribute<KrcmtAnyfRound, Integer> numberRounding;

	/** The time rounding. */
	public static volatile SingularAttribute<KrcmtAnyfRound, Integer> timeRounding;

	/** The amount rounding. */
	public static volatile SingularAttribute<KrcmtAnyfRound, Integer> amountRounding;

	/** The number rounding unit. */
	public static volatile SingularAttribute<KrcmtAnyfRound, Integer> numberRoundingUnit;

	/** The time rounding unit. */
	public static volatile SingularAttribute<KrcmtAnyfRound, Integer> timeRoundingUnit;

	/** The amount rounding unit. */
	public static volatile SingularAttribute<KrcmtAnyfRound, Integer> amountRoundingUnit;

}
