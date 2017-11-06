/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.entity.optitem.calculation;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KrcmtFormulaRounding_.
 */
@StaticMetamodel(KrcmtFormulaRounding.class)
public class KrcmtFormulaRounding_ {

	/** The krcmt formula rounding PK. */
	public static volatile SingularAttribute<KrcmtFormulaRounding, KrcmtFormulaRoundingPK> krcmtFormulaRoundingPK;

	/** The number rounding. */
	public static volatile SingularAttribute<KrcmtFormulaRounding, Integer> numberRounding;

	/** The time rounding. */
	public static volatile SingularAttribute<KrcmtFormulaRounding, Integer> timeRounding;

	/** The amount rounding. */
	public static volatile SingularAttribute<KrcmtFormulaRounding, Integer> amountRounding;

	/** The number rounding unit. */
	public static volatile SingularAttribute<KrcmtFormulaRounding, Integer> numberRoundingUnit;

	/** The time rounding unit. */
	public static volatile SingularAttribute<KrcmtFormulaRounding, Integer> timeRoundingUnit;

	/** The amount rounding unit. */
	public static volatile SingularAttribute<KrcmtFormulaRounding, Integer> amountRoundingUnit;

}
