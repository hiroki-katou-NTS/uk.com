/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.entity.company.organization.catetory;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class CclmtManagementCategory_.
 */
@StaticMetamodel(CclmtManagementCategory.class)
public class CclmtManagementCategory_ {

	/** The cclmt management category PK. */
	public static volatile SingularAttribute<CclmtManagementCategory, CclmtManagementCategoryPK> cclmtManagementCategoryPK;

	/** The name. */
	public static volatile SingularAttribute<CclmtManagementCategory, String> name;

}