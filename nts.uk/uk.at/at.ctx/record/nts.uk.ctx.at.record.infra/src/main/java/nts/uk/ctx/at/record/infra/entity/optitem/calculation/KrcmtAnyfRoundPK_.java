/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.entity.optitem.calculation;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KrcmtAnyfRoundPK_.
 */
@StaticMetamodel(KrcmtAnyfRoundPK.class)
public class KrcmtAnyfRoundPK_ {

	/** The cid. */
	public static volatile SingularAttribute<KrcmtAnyfRoundPK, String> cid;

	/** The optional item no. */
	public static volatile SingularAttribute<KrcmtAnyfRoundPK, Integer> optionalItemNo;

	/** The formula id. */
	public static volatile SingularAttribute<KrcmtAnyfRoundPK, String> formulaId;

	/** The rounding atr. */
	public static volatile SingularAttribute<KrcmtAnyfRoundPK, Integer> roundingAtr;

}
