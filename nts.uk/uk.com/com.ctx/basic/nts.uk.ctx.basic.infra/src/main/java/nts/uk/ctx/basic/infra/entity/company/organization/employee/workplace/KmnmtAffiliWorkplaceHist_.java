/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.entity.company.organization.employee.workplace;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import nts.arc.time.GeneralDate;

/**
 * The Class KmnmtAffiliWorkplaceHist_.
 */
@StaticMetamodel(KmnmtAffiliWorkplaceHist.class)
public class KmnmtAffiliWorkplaceHist_ {

	/** The kmnmt emp workplace hist PK. */
	public static volatile SingularAttribute<KmnmtAffiliWorkplaceHist, KmnmtAffiliWorkplaceHistPK> kmnmtAffiliWorkplaceHistPK;

	/** The str D. */
	public static volatile SingularAttribute<KmnmtAffiliWorkplaceHist, GeneralDate> strD;
	
	/** The end D. */
	public static volatile SingularAttribute<KmnmtAffiliWorkplaceHist, GeneralDate> endD;

}
