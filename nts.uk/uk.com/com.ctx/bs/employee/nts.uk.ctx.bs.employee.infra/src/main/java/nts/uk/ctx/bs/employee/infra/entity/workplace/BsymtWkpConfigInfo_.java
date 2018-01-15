/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.infra.entity.workplace;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class BsymtWkpConfigInfo_.
 */
@StaticMetamodel(BsymtWkpConfigInfo.class)
public class BsymtWkpConfigInfo_ {

	/** The bsymt wkp config info PK. */
	public static volatile SingularAttribute<BsymtWkpConfigInfo, BsymtWkpConfigInfoPK> bsymtWkpConfigInfoPK;

	/** The hierarchy cd. */
	public static volatile SingularAttribute<BsymtWkpConfigInfo, String> hierarchyCd;

	/** The bsymt wkp config. */
	public static volatile SingularAttribute<BsymtWkpConfigInfo, BsymtWkpConfig> bsymtWkpConfig;

}
