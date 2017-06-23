/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.entity.company.organization.employment.history;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import nts.arc.time.GeneralDate;

/**
 * The Class KmnmtEmploymentHist_.
 */
@StaticMetamodel(KmnmtEmploymentHist.class)
public class KmnmtEmploymentHist_ {
	

	/** The kmnmt employment hist PK. */
	public static volatile SingularAttribute<KmnmtEmploymentHist, KmnmtEmploymentHistPK> kmnmtEmploymentHistPK;
	
	/** The str D. */
	public static volatile SingularAttribute<KmnmtEmploymentHist, GeneralDate> strD;
	
	/** The end D. */
	public static volatile SingularAttribute<KmnmtEmploymentHist, GeneralDate> endD;

}
