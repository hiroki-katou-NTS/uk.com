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
	public static volatile SingularAttribute<KrcmtFormulaRounding, Short> numberRounding;

	/** The time rounding. */
	public static volatile SingularAttribute<KrcmtFormulaRounding, Short> timeRounding;

	/** The amount rounding. */
	public static volatile SingularAttribute<KrcmtFormulaRounding, Short> amountRounding;

	/** The number rounding unit. */
	public static volatile SingularAttribute<KrcmtFormulaRounding, Short> numberRoundingUnit;

	/** The time rounding unit. */
	public static volatile SingularAttribute<KrcmtFormulaRounding, Short> timeRoundingUnit;

	/** The amount rounding unit. */
	public static volatile SingularAttribute<KrcmtFormulaRounding, Short> amountRoundingUnit;

}
