/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.workrule.closure;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KshmtClosureHistPK_.
 */
@StaticMetamodel(KshmtClosureHistPK.class)
public class KshmtClosureHistPK_ {

	/** The ccid. */
	public static volatile SingularAttribute<KshmtClosureHistPK, String> cid;

	/** The closure id. */
	public static volatile SingularAttribute<KshmtClosureHistPK, Integer> closureId;
	
	/** The str YM. */
	public static volatile SingularAttribute<KshmtClosureHistPK, Integer> strYM;

}