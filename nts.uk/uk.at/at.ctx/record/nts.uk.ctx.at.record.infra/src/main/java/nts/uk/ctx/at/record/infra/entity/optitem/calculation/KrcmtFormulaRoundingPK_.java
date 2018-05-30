/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.entity.optitem.calculation;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KrcmtFormulaRoundingPK_.
 */
@StaticMetamodel(KrcmtFormulaRoundingPK.class)
public class KrcmtFormulaRoundingPK_ {

	/** The cid. */
	public static volatile SingularAttribute<KrcmtFormulaRoundingPK, String> cid;

	/** The optional item no. */
	public static volatile SingularAttribute<KrcmtFormulaRoundingPK, Integer> optionalItemNo;

	/** The formula id. */
	public static volatile SingularAttribute<KrcmtFormulaRoundingPK, String> formulaId;

	/** The rounding atr. */
	public static volatile SingularAttribute<KrcmtFormulaRoundingPK, Integer> roundingAtr;

}
