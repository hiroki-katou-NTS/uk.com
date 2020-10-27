/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.workrule.closure;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KshmtClosure_.
 */
@StaticMetamodel(KshmtClosure.class)
public class KshmtClosure_ {

	/** The kclmt closure PK. */
	public static volatile SingularAttribute<KshmtClosure, KshmtClosurePK> kshmtClosurePK;

	/** The use class. */
	public static volatile SingularAttribute<KshmtClosure, Integer> useClass;

	/** The month. */
	public static volatile SingularAttribute<KshmtClosure, Integer> closureMonth;


}