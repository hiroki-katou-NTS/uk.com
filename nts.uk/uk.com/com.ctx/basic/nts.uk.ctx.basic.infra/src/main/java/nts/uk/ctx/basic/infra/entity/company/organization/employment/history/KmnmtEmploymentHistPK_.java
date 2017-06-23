/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.entity.company.organization.employment.history;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class CemptEmployment_.
 */
@StaticMetamodel(KmnmtEmploymentHistPK.class)
public class KmnmtEmploymentHistPK_ {
	

	/** The hist id. */
	public static volatile SingularAttribute<KmnmtEmploymentHistPK, String> histId;
	
	/** The sid. */
	public static volatile SingularAttribute<KmnmtEmploymentHistPK, String> sid;
	
	/** The emptcd. */
	public static volatile SingularAttribute<KmnmtEmploymentHistPK, String> emptcd;
	
}
