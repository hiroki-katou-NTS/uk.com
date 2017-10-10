/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.infra.entity.jobtitle_old.affiliate;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import nts.arc.time.GeneralDate;

/**
 * The Class KmnmtAffiliJobTitleHistPK_.
 */
@StaticMetamodel(KmnmtAffiliJobTitleHistPK.class)
public class KmnmtAffiliJobTitleHistPK_ {

	/** The emp id. */
	public static volatile SingularAttribute<KmnmtAffiliJobTitleHistPK, String> empId;

	/** The job id. */
	public static volatile SingularAttribute<KmnmtAffiliJobTitleHistPK, String> jobId;
	
	/** The str D. */
	public static volatile SingularAttribute<KmnmtAffiliJobTitleHistPK, GeneralDate> strD;
}
