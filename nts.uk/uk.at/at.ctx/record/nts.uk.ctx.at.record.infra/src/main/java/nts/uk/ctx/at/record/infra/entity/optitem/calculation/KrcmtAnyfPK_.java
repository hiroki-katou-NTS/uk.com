/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.entity.optitem.calculation;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KrcmtAnyfPK_.
 */
@StaticMetamodel(KrcmtAnyfPK.class)
public class KrcmtAnyfPK_ {

	/** The cid. */
	public static volatile SingularAttribute<KrcmtAnyfPK, String> cid;

	/** The optional item no. */
	public static volatile SingularAttribute<KrcmtAnyfPK, Integer> optionalItemNo;

	/** The formula id. */
	public static volatile SingularAttribute<KrcmtAnyfPK, String> formulaId;

}
