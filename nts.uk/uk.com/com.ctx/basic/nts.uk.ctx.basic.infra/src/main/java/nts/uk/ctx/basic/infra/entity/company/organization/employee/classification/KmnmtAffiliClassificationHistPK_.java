/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.entity.company.organization.employee.classification;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KmnmtAffiliClassificationHistPK_.
 */
@StaticMetamodel(KmnmtAffiliClassificationHistPK.class)
public class KmnmtAffiliClassificationHistPK_ {

	/** The hist id. */
	public static volatile SingularAttribute<KmnmtAffiliClassificationHistPK, String> histId;

	/** The emp id. */
	public static volatile SingularAttribute<KmnmtAffiliClassificationHistPK, String> empId;
	
	/** The clscd. */
	public static volatile SingularAttribute<KmnmtAffiliClassificationHistPK, String> clscd;

}
