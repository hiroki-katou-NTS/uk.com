/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.infra.entity.workplace;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class BsymtWkpConfigInfoPK_.
 */
@StaticMetamodel(BsymtWkpConfigInfoPK.class)
public class BsymtWkpConfigInfoPK_ {

	/** The cid. */
	public static volatile SingularAttribute<BsymtWkpConfigInfoPK, String> cid;
	
	/** The history id. */
	public static volatile SingularAttribute<BsymtWkpConfigInfoPK, String> historyId;
	
	/** The wkpid. */
	public static volatile SingularAttribute<BsymtWkpConfigInfoPK, String> wkpid;
	
}

