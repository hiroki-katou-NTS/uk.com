/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.entity.company.organization.classification;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class CclmtManagementCategory_.
 */
@StaticMetamodel(CclmtClassification.class)
public class CclmtClassification_ {

	/** The cclmt management category PK. */
	public static volatile SingularAttribute<CclmtClassification, CclmtClassificationPK> cclmtClassificationPK;

	/** The name. */
	public static volatile SingularAttribute<CclmtClassification, String> name;

}