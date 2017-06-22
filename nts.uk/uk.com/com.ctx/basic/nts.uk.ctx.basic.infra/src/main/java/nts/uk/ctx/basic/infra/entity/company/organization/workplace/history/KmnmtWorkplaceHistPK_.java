/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.entity.company.organization.workplace.history;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KmnmtWorkplaceHistPK_.
 */
@StaticMetamodel(KmnmtWorkplaceHistPK.class)
public class KmnmtWorkplaceHistPK_ {

	/** The hist id. */
	public static volatile SingularAttribute<KmnmtWorkplaceHistPK, String> histId;

	/** The sid. */
	public static volatile SingularAttribute<KmnmtWorkplaceHistPK, String> sid;
	
	/** The wpl id. */
	public static volatile SingularAttribute<KmnmtWorkplaceHistPK, String> wplId;

}
