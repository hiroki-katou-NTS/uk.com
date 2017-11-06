/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.infra.entity.workplace;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class BsymtWorkplaceInfoPK_.
 */
@StaticMetamodel(BsymtWorkplaceInfoPK.class)
public class BsymtWorkplaceInfoPK_ {

	/** The cid. */
	public static volatile SingularAttribute<BsymtWorkplaceInfoPK, String> cid;
	
	/** The history id. */
	public static volatile SingularAttribute<BsymtWorkplaceInfoPK, String> historyId;
	
	/** The wkpid. */
	public static volatile SingularAttribute<BsymtWorkplaceInfoPK, String> wkpid;
}