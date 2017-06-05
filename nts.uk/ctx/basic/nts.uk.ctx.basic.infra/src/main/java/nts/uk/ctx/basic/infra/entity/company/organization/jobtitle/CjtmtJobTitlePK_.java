/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.entity.company.organization.jobtitle;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class CjtmtJobTitlePK_.
 */
@StaticMetamodel(CjtmtJobTitlePK.class)
public class CjtmtJobTitlePK_ {

	/** The company id. */
	public static volatile SingularAttribute<CjtmtJobTitlePK, String> companyId;

	/** The job id. */
	public static volatile SingularAttribute<CjtmtJobTitlePK, String> jobId;

	/** The job code. */
	public static volatile SingularAttribute<CjtmtJobTitlePK, String> jobCode;
}
