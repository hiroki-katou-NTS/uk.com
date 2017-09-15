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
@StaticMetamodel(BsymtWorkplacePK.class)
public class BsymtWorkplacePK_ {

	/** The cid. */
	public static volatile SingularAttribute<BsymtWorkplacePK, String> cid;

	/** The wkpid. */
	public static volatile SingularAttribute<BsymtWorkplacePK, String> wkpid;
	
	/** The history id. */
	public static volatile SingularAttribute<BsymtWorkplacePK, String> historyId;
}
