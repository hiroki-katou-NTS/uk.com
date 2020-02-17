/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.infra.entity.workplace;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import nts.arc.time.GeneralDate;

/**
 * The Class BsymtWkpConfig_.
 */
@StaticMetamodel(BsymtWkpConfig.class)
public class BsymtWkpConfig_ {
	/** The bsymt wkp config PK. */
	public static volatile SingularAttribute<BsymtWkpConfig, BsymtWkpConfigPK> bsymtWkpConfigPK;

	/** The str D. */
	public static volatile SingularAttribute<BsymtWkpConfig, GeneralDate> strD;

	/** The end D. */
	public static volatile SingularAttribute<BsymtWkpConfig, GeneralDate> endD;
	
	/** The bsymt wkp config infos. */
	public static volatile ListAttribute<BsymtWkpConfig, BsymtWkpConfigInfo> bsymtWkpConfigInfos;

}
