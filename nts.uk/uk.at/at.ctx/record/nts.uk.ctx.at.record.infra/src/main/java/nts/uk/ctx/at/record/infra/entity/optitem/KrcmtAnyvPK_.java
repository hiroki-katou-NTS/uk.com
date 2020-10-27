/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.entity.optitem;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KrcmtAnyvPK_.
 */
@StaticMetamodel(KrcmtAnyvPK.class)
public class KrcmtAnyvPK_ {

	/** The cid. */
	public static volatile SingularAttribute<KrcmtAnyvPK, String> cid;

	/** The optional item no. */
	public static volatile SingularAttribute<KrcmtAnyvPK, Integer> optionalItemNo;

}
