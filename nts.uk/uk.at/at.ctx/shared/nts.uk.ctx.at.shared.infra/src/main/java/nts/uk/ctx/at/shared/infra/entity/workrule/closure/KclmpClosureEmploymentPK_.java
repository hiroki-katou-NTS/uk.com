/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.workrule.closure;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KclmpClosureEmploymentPK_.
 */
@StaticMetamodel(KclmpClosureEmploymentPK.class)
public class KclmpClosureEmploymentPK_ {

	/** The company id. */
	public static volatile SingularAttribute<KclmpClosureEmploymentPK, String> companyId;
	
	/** The employment CD. */
	public static volatile SingularAttribute<KclmpClosureEmploymentPK, String> employmentCD;
}
