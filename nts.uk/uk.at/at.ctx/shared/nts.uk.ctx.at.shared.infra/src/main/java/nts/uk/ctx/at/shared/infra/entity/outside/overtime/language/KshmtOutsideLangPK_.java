/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.outside.overtime.language;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KshmtOutsideLangPK_.
 */
@StaticMetamodel(KshmtOutsideLangPK.class)
public class KshmtOutsideLangPK_ {

	/** The cid. */
	public static volatile SingularAttribute<KshmtOutsideLangPK, String> cid;
	
	/** The over time no. */
	public static volatile SingularAttribute<KshmtOutsideLangPK, Integer> overTimeNo;
	
	/** The language id. */
	public static volatile SingularAttribute<KshmtOutsideLangPK, String> languageId;
	
}
