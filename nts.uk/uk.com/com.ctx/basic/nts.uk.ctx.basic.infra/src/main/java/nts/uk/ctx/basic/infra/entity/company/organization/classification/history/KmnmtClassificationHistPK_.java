/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.entity.company.organization.classification.history;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KmnmtWorkplaceHistPK_.
 */
@StaticMetamodel(KmnmtClassificationHistPK.class)
public class KmnmtClassificationHistPK_ {

	/** The hist id. */
	public static volatile SingularAttribute<KmnmtClassificationHistPK, String> histId;

	/** The sid. */
	public static volatile SingularAttribute<KmnmtClassificationHistPK, String> sid;
	
	/** The clscd. */
	public static volatile SingularAttribute<KmnmtClassificationHistPK, String> clscd;

}
