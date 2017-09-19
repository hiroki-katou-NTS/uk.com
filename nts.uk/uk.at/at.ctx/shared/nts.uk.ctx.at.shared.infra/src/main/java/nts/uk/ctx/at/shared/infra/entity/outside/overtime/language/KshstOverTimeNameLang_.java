/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.outside.overtime.language;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KshstOverTimeNameLang_.
 */
@StaticMetamodel(KshstOverTimeNameLang.class)
public class KshstOverTimeNameLang_ {

	/** The kshst over time name lang PK. */
	public static volatile SingularAttribute<KshstOverTimeNameLang, KshstOverTimeNameLangPK> kshstOverTimeNameLangPK;
	
	/** The name. */
	public static volatile SingularAttribute<KshstOverTimeNameLang, String> name;
	
}
