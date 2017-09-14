/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.infra.entity.workplace;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import nts.arc.time.GeneralDate;

/**
 * The Class BsydtWkpConfig_.
 */
@StaticMetamodel(BsydtWkpConfig.class)
public class BsydtWkpConfig_ {

	/** The bsydt wkp config PK. */
	public static volatile SingularAttribute<BsydtWkpConfig, BsydtWkpConfigPK> bsydtWkpConfigPK;
	
	/** The str D. */
	public static volatile SingularAttribute<BsydtWkpConfig, GeneralDate> strD;
	
	/** The end D. */
	public static volatile SingularAttribute<BsydtWkpConfig, GeneralDate> endD;
}
