/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.entity.company.organization.employment;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class CemptEmploymentPK_.
 */
@StaticMetamodel(CemptEmploymentPK.class)
public class CemptEmploymentPK_ {

	/** The company id. */
	public static volatile SingularAttribute<CemptEmploymentPK, String> cid;
	
	/** The code. */
	public static volatile SingularAttribute<CemptEmploymentPK, String> code;
	
}
