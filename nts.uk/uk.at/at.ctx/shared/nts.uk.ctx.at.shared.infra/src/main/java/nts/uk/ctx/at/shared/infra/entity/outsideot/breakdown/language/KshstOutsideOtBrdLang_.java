/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.outsideot.breakdown.language;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KshstOverTimeLangBrd_.
 */
@StaticMetamodel(KshmtOutsideDetailLang.class)
public class KshstOutsideOtBrdLang_ {

	/** The kshst over time lang brd PK. */
	public static volatile SingularAttribute<KshmtOutsideDetailLang, KshstOutsideOtBrdLangPK> kshstOutsideOtBrdLangPK;
	
	/** The name. */
	public static volatile SingularAttribute<KshmtOutsideDetailLang, String> name;
	
}
