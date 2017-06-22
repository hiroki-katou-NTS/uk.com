/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.entity.company.organization.classification.history;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import nts.arc.time.GeneralDate;

/**
 * The Class KmnmtWorkplaceHist_.
 */
@StaticMetamodel(KmnmtClassificationHist.class)
public class KmnmtClassificationHist_ {

	/** The kmnmt emp workplace hist PK. */
	public static volatile SingularAttribute<KmnmtClassificationHist, KmnmtClassificationHistPK> kmnmtClassificationHistPK;

	/** The str D. */
	public static volatile SingularAttribute<KmnmtClassificationHist, GeneralDate> strD;
	
	/** The end D. */
	public static volatile SingularAttribute<KmnmtClassificationHist, GeneralDate> endD;

}
