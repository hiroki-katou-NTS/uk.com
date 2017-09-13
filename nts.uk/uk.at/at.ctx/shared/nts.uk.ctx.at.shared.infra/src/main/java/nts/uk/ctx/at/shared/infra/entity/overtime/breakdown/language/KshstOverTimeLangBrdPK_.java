/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.overtime.breakdown.language;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KshstOverTimeLangBrdPK_.
 */
@StaticMetamodel(KshstOverTimeLangBrdPK.class)
public class KshstOverTimeLangBrdPK_ {

	/** The cid. */
	public static volatile SingularAttribute<KshstOverTimeLangBrdPK, String> cid;
	
	/** The brd item no. */
	public static volatile SingularAttribute<KshstOverTimeLangBrdPK, Integer> brdItemNo;
	
	/** The language id. */
	public static volatile SingularAttribute<KshstOverTimeLangBrdPK, String> languageId;
	
}
