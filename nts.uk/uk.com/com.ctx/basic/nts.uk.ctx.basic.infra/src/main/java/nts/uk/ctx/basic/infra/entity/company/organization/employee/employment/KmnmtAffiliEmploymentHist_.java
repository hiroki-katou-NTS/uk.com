/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.entity.company.organization.employee.employment;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import nts.arc.time.GeneralDate;

/**
 * The Class KmnmtAffiliEmploymentHist_.
 */
@StaticMetamodel(KmnmtAffiliEmploymentHist.class)
public class KmnmtAffiliEmploymentHist_ {
	

	/** The kmnmt employment hist PK. */
	public static volatile SingularAttribute<KmnmtAffiliEmploymentHist, KmnmtAffiliEmploymentHistPK> kmnmtEmploymentHistPK;
	
	/** The str D. */
	public static volatile SingularAttribute<KmnmtAffiliEmploymentHist, GeneralDate> strD;
	
	/** The end D. */
	public static volatile SingularAttribute<KmnmtAffiliEmploymentHist, GeneralDate> endD;

}
