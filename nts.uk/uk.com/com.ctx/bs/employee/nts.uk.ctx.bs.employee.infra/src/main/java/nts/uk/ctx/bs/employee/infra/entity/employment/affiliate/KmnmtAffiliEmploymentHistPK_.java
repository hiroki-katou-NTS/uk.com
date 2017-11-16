/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.infra.entity.employment.affiliate;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import nts.arc.time.GeneralDate;

/**
 * The Class KmnmtAffiliEmploymentHistPK_.
 */
@StaticMetamodel(KmnmtAffiliEmploymentHistPK.class)
public class KmnmtAffiliEmploymentHistPK_ {
		
	/** The emp id. */
	public static volatile SingularAttribute<KmnmtAffiliEmploymentHistPK, String> empId;
	
	/** The emptcd. */
	public static volatile SingularAttribute<KmnmtAffiliEmploymentHistPK, String> emptcd;
	
	/** The str D. */
	public static volatile SingularAttribute<KmnmtAffiliEmploymentHistPK, GeneralDate> strD;
}
