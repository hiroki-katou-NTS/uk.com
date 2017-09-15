/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.infra.entity.workplace;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import nts.arc.time.GeneralDate;

/**
 * The Class BsymtWorkplace_.
 */
@StaticMetamodel(BsymtWorkplace.class)
public class BsymtWorkplace_ {

	/** The bsymt workplace PK. */
	public static volatile SingularAttribute<BsymtWorkplace, BsymtWorkplacePK> bsymtWorkplacePK;
	
	/** The str D. */
	public static volatile SingularAttribute<BsymtWorkplace, GeneralDate> strD;
	
	/** The end D. */
	public static volatile SingularAttribute<BsymtWorkplace, GeneralDate> endD;
}
