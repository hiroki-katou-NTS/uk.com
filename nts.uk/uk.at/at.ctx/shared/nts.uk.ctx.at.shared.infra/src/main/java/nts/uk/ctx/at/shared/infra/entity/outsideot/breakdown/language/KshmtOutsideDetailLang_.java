/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.outsideot.breakdown.language;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KshmtOutsideLangBrd_.
 */
@StaticMetamodel(KshmtOutsideDetailLang.class)
public class KshmtOutsideDetailLang_ {

	/** The kshst over time lang brd PK. */
	public static volatile SingularAttribute<KshmtOutsideDetailLang, KshmtOutsideDetailLangPK> kshmtOutsideDetailLangPK;
	
	/** The name. */
	public static volatile SingularAttribute<KshmtOutsideDetailLang, String> name;
	
}
