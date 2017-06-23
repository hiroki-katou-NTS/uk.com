/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.entity.company.organization.employee.classification;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import nts.arc.time.GeneralDate;

/**
 * The Class KmnmtAffiliClassificationHist_.
 */
@StaticMetamodel(KmnmtAffiliClassificationHist.class)
public class KmnmtAffiliClassificationHist_ {

	/** The kmnmt classification hist PK. */
	public static volatile SingularAttribute<KmnmtAffiliClassificationHist, KmnmtAffiliClassificationHistPK> kmnmtClassificationHistPK;

	/** The str D. */
	public static volatile SingularAttribute<KmnmtAffiliClassificationHist, GeneralDate> strD;
	
	/** The end D. */
	public static volatile SingularAttribute<KmnmtAffiliClassificationHist, GeneralDate> endD;

}
