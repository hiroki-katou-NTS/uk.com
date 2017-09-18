/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.infra.entity.workplace;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import nts.arc.time.GeneralDate;

/**
 * The Class BsymtWorkplaceHist_.
 */
@StaticMetamodel(BsymtWorkplaceHist.class)
public class BsymtWorkplaceHist_ {

	/** The bsymt workplace hist PK. */
	public static volatile SingularAttribute<BsymtWorkplaceHist, BsymtWorkplaceHistPK> bsymtWorkplaceHistPK;
	
	/** The str D. */
	public static volatile SingularAttribute<BsymtWorkplaceHist, GeneralDate> strD;
	
	/** The end D. */
	public static volatile SingularAttribute<BsymtWorkplaceHist, GeneralDate> endD;
}
