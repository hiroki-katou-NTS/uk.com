/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.outsideot.breakdown.language;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KshstOutsideOtBrdLangPK_.
 */
@StaticMetamodel(KshstOutsideOtBrdLangPK.class)
public class KshstOutsideOtBrdLangPK_ {

	/** The cid. */
	public static volatile SingularAttribute<KshstOutsideOtBrdLangPK, String> cid;
	
	/** The brd item no. */
	public static volatile SingularAttribute<KshstOutsideOtBrdLangPK, Integer> brdItemNo;
	
	/** The language id. */
	public static volatile SingularAttribute<KshstOutsideOtBrdLangPK, String> languageId;
	
}
