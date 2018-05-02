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
@StaticMetamodel(KclmtClosure.class)
public class KclmtClosure_ {

	/** The kclmt closure PK. */
	public static volatile SingularAttribute<KclmtClosure, KclmtClosurePK> kclmtClosurePK;

	/** The use class. */
	public static volatile SingularAttribute<KclmtClosure, Integer> useClass;

	/** The closure month. */
	public static volatile SingularAttribute<KclmtClosure, Integer> closureMonth;
	
	/** The closure classification. */
	public static volatile SingularAttribute<KclmtClosure, Integer> closureClassification;

}