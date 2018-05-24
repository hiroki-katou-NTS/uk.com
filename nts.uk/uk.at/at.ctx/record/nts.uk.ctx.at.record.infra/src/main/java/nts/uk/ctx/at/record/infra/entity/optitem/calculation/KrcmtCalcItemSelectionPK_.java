/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.entity.optitem.calculation;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KrcmtCalcItemSelectionPK_.
 */
@StaticMetamodel(KrcmtCalcItemSelectionPK.class)
public class KrcmtCalcItemSelectionPK_ {

	/** The cid. */
	public static volatile SingularAttribute<KrcmtCalcItemSelectionPK, String> cid;

	/** The optional item no. */
	public static volatile SingularAttribute<KrcmtCalcItemSelectionPK, Integer> optionalItemNo;

	/** The formula id. */
	public static volatile SingularAttribute<KrcmtCalcItemSelectionPK, String> formulaId;

	/** The attendance item id. */
	public static volatile SingularAttribute<KrcmtCalcItemSelectionPK, String> attendanceItemId;

}
