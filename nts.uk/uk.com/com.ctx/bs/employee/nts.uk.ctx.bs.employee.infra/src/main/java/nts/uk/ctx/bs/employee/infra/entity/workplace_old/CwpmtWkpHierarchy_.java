/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.infra.entity.workplace_old;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class CwpmtWkpHierarchy_.
 */
@StaticMetamodel(CwpmtWkpHierarchy.class)
public class CwpmtWkpHierarchy_ {

	/** The cwpmt wkp hierarchy PK. */
	public static volatile SingularAttribute<CwpmtWkpHierarchy, CwpmtWkpHierarchyPK> cwpmtWkpHierarchyPK;
	
	/** The hierarchy cd. */
	public static volatile SingularAttribute<CwpmtWkpHierarchy, String> hierarchyCd;

}