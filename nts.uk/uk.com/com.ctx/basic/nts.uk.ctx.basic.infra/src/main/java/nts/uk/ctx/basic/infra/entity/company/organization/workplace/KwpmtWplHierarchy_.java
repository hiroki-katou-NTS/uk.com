/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.entity.company.organization.workplace;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KwpmtWplHierarchy_.
 */
@StaticMetamodel(KwpmtWplHierarchy.class)
public class KwpmtWplHierarchy_ {

	/** The kwpmt wpl hierarchy PK. */
	public static volatile SingularAttribute<KwpmtWplHierarchy, KwpmtWplHierarchyPK> kwpmtWplHierarchyPK;
	
	/** The wkpcd. */
	public static volatile SingularAttribute<KwpmtWplHierarchy, String> wkpcd;

}