/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.entity.company.organization.workplace;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class CclmtManagementCategoryPK_.
 */
@StaticMetamodel(KwpmtWorkHierarchyPK.class)
public class KwpmtWorkHierarchyPK_ {

	/** The ccid. */
	public static volatile SingularAttribute<KwpmtWorkHierarchyPK, String> ccid;

	/** The wkpid. */
	public static volatile SingularAttribute<KwpmtWorkHierarchyPK, String> wkpid;
	
	/** The wkpcd. */
	public static volatile SingularAttribute<KwpmtWorkHierarchyPK, String> wkpcd;
	
	/** The his id. */
	public static volatile SingularAttribute<KwpmtWorkHierarchyPK, String> hisId;

}