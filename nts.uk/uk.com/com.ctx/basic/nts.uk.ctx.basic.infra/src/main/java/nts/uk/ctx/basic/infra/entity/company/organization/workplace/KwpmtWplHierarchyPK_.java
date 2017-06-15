/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.entity.company.organization.workplace;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KwpmtWplHierarchyPK_.
 */
@StaticMetamodel(KwpmtWplHierarchyPK.class)
public class KwpmtWplHierarchyPK_ {

	/** The ccid. */
	public static volatile SingularAttribute<KwpmtWplHierarchyPK, String> cid;

	/** The wkpid. */
	public static volatile SingularAttribute<KwpmtWplHierarchyPK, String> wkpid;
	
	/** The his id. */
	public static volatile SingularAttribute<KwpmtWplHierarchyPK, String> hisId;

}