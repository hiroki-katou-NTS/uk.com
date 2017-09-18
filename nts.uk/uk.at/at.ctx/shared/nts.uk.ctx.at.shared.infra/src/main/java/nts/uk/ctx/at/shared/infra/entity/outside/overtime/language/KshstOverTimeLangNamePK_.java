/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.outside.overtime.language;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KshstOverTimeLangName_.
 */
@StaticMetamodel(KshstOverTimeLangNamePK.class)
public class KshstOverTimeLangNamePK_ {

	/** The cid. */
	public static volatile SingularAttribute<KshstOverTimeLangNamePK, String> cid;
	
	/** The over time no. */
	public static volatile SingularAttribute<KshstOverTimeLangNamePK, Integer> overTimeNo;
	
	/** The language id. */
	public static volatile SingularAttribute<KshstOverTimeLangNamePK, String> languageId;
	
}
