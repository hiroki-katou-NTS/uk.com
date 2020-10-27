/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.outsideot.breakdown.language;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KshmtOutsideDetailLangPK_.
 */
@StaticMetamodel(KshmtOutsideDetailLangPK.class)
public class KshmtOutsideDetailLangPK_ {

	/** The cid. */
	public static volatile SingularAttribute<KshmtOutsideDetailLangPK, String> cid;
	
	/** The brd item no. */
	public static volatile SingularAttribute<KshmtOutsideDetailLangPK, Integer> brdItemNo;
	
	/** The language id. */
	public static volatile SingularAttribute<KshmtOutsideDetailLangPK, String> languageId;
	
}
