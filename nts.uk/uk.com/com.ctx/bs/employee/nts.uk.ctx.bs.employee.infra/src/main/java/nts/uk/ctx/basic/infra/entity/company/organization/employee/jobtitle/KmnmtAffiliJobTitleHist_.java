/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.entity.company.organization.employee.jobtitle;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import nts.arc.time.GeneralDate;

/**
 * The Class KmnmtAffiliJobTitleHist_.
 */
@StaticMetamodel(KmnmtAffiliJobTitleHist.class)
public class KmnmtAffiliJobTitleHist_ {

	/** The kmnmt job title hist PK. */
	public static volatile SingularAttribute<KmnmtAffiliJobTitleHist, KmnmtAffiliJobTitleHistPK> kmnmtJobTitleHistPK;

	/** The end D. */
	public static volatile SingularAttribute<KmnmtAffiliJobTitleHist, GeneralDate> endD;
}
