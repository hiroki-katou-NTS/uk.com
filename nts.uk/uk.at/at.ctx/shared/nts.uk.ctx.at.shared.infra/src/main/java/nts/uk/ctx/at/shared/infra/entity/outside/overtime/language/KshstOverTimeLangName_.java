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
@StaticMetamodel(KshstOverTimeLangName.class)
public class KshstOverTimeLangName_ {

	/** The kshst over time language name PK. */
	public static volatile SingularAttribute<KshstOverTimeLangName, KshstOverTimeLangNamePK> kshstOverTimeLangNamePK;
	
	/** The name. */
	public static volatile SingularAttribute<KshstOverTimeLangName, String> name;
	
}
