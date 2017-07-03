/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.entity.company.organization.employee.employment;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KmnmtAffiliEmploymentHistPK_.
 */
@StaticMetamodel(KmnmtAffiliEmploymentHistPK.class)
public class KmnmtAffiliEmploymentHistPK_ {
	

	/** The hist id. */
	public static volatile SingularAttribute<KmnmtAffiliEmploymentHistPK, String> histId;
	
	/** The emp id. */
	public static volatile SingularAttribute<KmnmtAffiliEmploymentHistPK, String> empId;
	
	/** The emptcd. */
	public static volatile SingularAttribute<KmnmtAffiliEmploymentHistPK, String> emptcd;
	
}
