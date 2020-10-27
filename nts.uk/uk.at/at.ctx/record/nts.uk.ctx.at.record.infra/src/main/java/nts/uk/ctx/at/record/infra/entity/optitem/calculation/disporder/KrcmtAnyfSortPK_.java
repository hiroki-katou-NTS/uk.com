/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.entity.optitem.calculation.disporder;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KrcmtAnyfSortPK_.
 */
@StaticMetamodel(KrcmtAnyfSortPK.class)
public class KrcmtAnyfSortPK_ {

	/** The cid. */
	public static volatile SingularAttribute<KrcmtAnyfSortPK, String> cid;

	/** The optional item no. */
	public static volatile SingularAttribute<KrcmtAnyfSortPK, Integer> optionalItemNo;

	/** The formula id. */
	public static volatile SingularAttribute<KrcmtAnyfSortPK, String> formulaId;

}
