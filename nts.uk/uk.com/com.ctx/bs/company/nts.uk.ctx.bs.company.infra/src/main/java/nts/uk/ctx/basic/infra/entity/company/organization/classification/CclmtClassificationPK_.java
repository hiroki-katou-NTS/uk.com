/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.entity.company.organization.classification;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class CclmtManagementCategoryPK_.
 */
@StaticMetamodel(CclmtClassificationPK.class)
public class CclmtClassificationPK_ {

	/** The ccid. */
	public static volatile SingularAttribute<CclmtClassificationPK, String> cid;

	/** The code. */
	public static volatile SingularAttribute<CclmtClassificationPK, String> code;

}