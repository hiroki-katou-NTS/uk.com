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

	/** The sid. */
	public static volatile SingularAttribute<KmnmtAffiliJobTitleHistPK, String> sid;

	/** The pos id. */
	public static volatile SingularAttribute<KmnmtAffiliJobTitleHistPK, String> posId;
}
