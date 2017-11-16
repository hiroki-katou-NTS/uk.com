/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.infra.entity.classification.affiliate;

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
	
	/** The end D. */
	public static volatile SingularAttribute<KmnmtAffiliClassificationHist, GeneralDate> endD;

}
