/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.entity.optitem.calculation.disporder;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KrcstFormulaDisporderPK_.
 */
@StaticMetamodel(KrcstFormulaDisporderPK.class)
public class KrcstFormulaDisporderPK_ {

	/** The cid. */
	public static volatile SingularAttribute<KrcstFormulaDisporderPK, String> cid;

	/** The optional item no. */
	public static volatile SingularAttribute<KrcstFormulaDisporderPK, Integer> optionalItemNo;

	/** The formula id. */
	public static volatile SingularAttribute<KrcstFormulaDisporderPK, String> formulaId;

}
