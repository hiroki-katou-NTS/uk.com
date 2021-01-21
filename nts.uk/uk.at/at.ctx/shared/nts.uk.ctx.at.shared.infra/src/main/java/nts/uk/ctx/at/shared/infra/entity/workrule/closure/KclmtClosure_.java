/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.workrule.closure;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KclmtClosure_.
 */
@StaticMetamodel(KshmtClosure.class)
public class KclmtClosure_ {

	/** The kclmt closure PK. */
	public static volatile SingularAttribute<KshmtClosure, KclmtClosurePK> kclmtClosurePK;

	/** The use class. */
	public static volatile SingularAttribute<KshmtClosure, Integer> useClass;

	/** The month. */
	public static volatile SingularAttribute<KshmtClosure, Integer> closureMonth;


}