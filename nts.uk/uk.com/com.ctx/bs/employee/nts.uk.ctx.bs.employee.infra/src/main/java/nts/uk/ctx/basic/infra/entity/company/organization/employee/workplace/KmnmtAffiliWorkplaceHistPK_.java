/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.entity.company.organization.employee.workplace;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import nts.arc.time.GeneralDate;

/**
 * The Class KmnmtAffiliWorkplaceHistPK_.
 */
@StaticMetamodel(KmnmtAffiliWorkplaceHistPK.class)
public class KmnmtAffiliWorkplaceHistPK_ {


	/** The emp id. */
	public static volatile SingularAttribute<KmnmtAffiliWorkplaceHistPK, String> empId;
	
	/** The wkp id. */
	public static volatile SingularAttribute<KmnmtAffiliWorkplaceHistPK, String> wkpId;
	
	/** The str D. */
	public static volatile SingularAttribute<KmnmtAffiliWorkplaceHistPK, GeneralDate> strD;

}
