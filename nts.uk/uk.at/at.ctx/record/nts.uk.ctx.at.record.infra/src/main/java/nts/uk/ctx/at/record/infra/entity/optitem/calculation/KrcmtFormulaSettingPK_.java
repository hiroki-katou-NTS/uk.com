/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.entity.optitem.calculation;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KrcmtFormulaSettingPK_.
 */
@StaticMetamodel(KrcmtFormulaSettingPK.class)
public class KrcmtFormulaSettingPK_ {

	/** The cid. */
	public static volatile SingularAttribute<KrcmtFormulaSettingPK, String> cid;

	/** The optional item no. */
	public static volatile SingularAttribute<KrcmtFormulaSettingPK, Integer> optionalItemNo;

	/** The formula id. */
	public static volatile SingularAttribute<KrcmtFormulaSettingPK, String> formulaId;

}
