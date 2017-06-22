/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.entity.company.organization.workplace.history;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import nts.arc.time.GeneralDate;

/**
 * The Class KmnmtWorkplaceHist_.
 */
@StaticMetamodel(KmnmtWorkplaceHist.class)
public class KmnmtWorkplaceHist_ {

	/** The kmnmt emp workplace hist PK. */
	public static volatile SingularAttribute<KmnmtWorkplaceHist, KmnmtWorkplaceHistPK> kmnmtEmpWorkplaceHistPK;

	/** The str D. */
	public static volatile SingularAttribute<KmnmtWorkplaceHist, GeneralDate> strD;
	
	/** The end D. */
	public static volatile SingularAttribute<KmnmtWorkplaceHist, GeneralDate> endD;

}
