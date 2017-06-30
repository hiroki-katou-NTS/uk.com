/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.entity.company.organization.employee.jobtitle;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KmnmtAffiliJobTitleHistPK_.
 */
@StaticMetamodel(KmnmtAffiliJobTitleHistPK.class)
public class KmnmtAffiliJobTitleHistPK_ {

	/** The hist id. */
	public static volatile SingularAttribute<KmnmtAffiliJobTitleHistPK, String> histId;

	/** The emp id. */
	public static volatile SingularAttribute<KmnmtAffiliJobTitleHistPK, String> empId;

	/** The job id. */
	public static volatile SingularAttribute<KmnmtAffiliJobTitleHistPK, String> jobId;
}
