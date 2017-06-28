/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.entity.company.organization.jobtitle;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class CsqmtSequenceMaster_.
 */
@StaticMetamodel(CsqmtSequenceMaster.class)
public class CsqmtSequenceMaster_ {

	/** The csqmt sequence master PK. */
	public static volatile SingularAttribute<CsqmtSequenceMaster, CsqmtSequenceMasterPK> csqmtSequenceMasterPK;

	/** The order. */
	public static volatile SingularAttribute<CsqmtSequenceMaster, String> order;

	/** The sequence name. */
	public static volatile SingularAttribute<CsqmtSequenceMaster, String> sequenceName;
}
