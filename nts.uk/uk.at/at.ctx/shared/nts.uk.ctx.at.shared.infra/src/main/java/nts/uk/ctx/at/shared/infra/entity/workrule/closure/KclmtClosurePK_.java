/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.workrule.closure;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KclmtClosurePK_.
 */
@StaticMetamodel(KclmtClosurePK.class)
public class KclmtClosurePK_ {

	/** The ccid. */
	public static volatile SingularAttribute<KclmtClosurePK, String> cid;

	/** The closure id. */
	public static volatile SingularAttribute<KclmtClosurePK, String> closureId;

}