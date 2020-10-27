/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.workrule.closure;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KshmtClosureEmp_.
 */
@StaticMetamodel(KshmtClosureEmp.class)
public class KshmtClosureEmp_ {

	/** The kclmp closure employment PK. */
	public static volatile SingularAttribute<KshmtClosureEmp, KclmpClosureEmploymentPK> kclmpClosureEmploymentPK;
	
	/** The closure id. */
	public static volatile SingularAttribute<KshmtClosureEmp, Integer> closureId;
}
