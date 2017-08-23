/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.entity.company.organization.jobtitle;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class CsqmtSequenceMasterPK_.
 */
@StaticMetamodel(CsqmtSequenceMasterPK.class)
public class CsqmtSequenceMasterPK_ {

	/** The company id. */
	public static volatile SingularAttribute<CsqmtSequenceMasterPK, String> companyId;

	/** The sequence code. */
	public static volatile SingularAttribute<CsqmtSequenceMasterPK, String> sequenceCode;
}
