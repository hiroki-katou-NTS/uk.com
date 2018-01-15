/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.infra.entity.workplace;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class BsymtWorkplacePK_.
 */
@StaticMetamodel(BsymtWorkplaceHistPK.class)
public class BsymtWorkplaceHistPK_ {

	/** The cid. */
	public static volatile SingularAttribute<BsymtWorkplaceHistPK, String> cid;

	/** The wkpid. */
	public static volatile SingularAttribute<BsymtWorkplaceHistPK, String> wkpid;
	
	/** The history id. */
	public static volatile SingularAttribute<BsymtWorkplaceHistPK, String> historyId;
}
