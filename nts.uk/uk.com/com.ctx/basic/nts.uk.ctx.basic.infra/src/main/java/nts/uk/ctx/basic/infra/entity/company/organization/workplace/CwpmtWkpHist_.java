/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.entity.company.organization.workplace;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import nts.arc.time.GeneralDate;

/**
 * The Class CwpmtWkpHist_.
 */
@StaticMetamodel(CwpmtWkpHist.class)
public class CwpmtWkpHist_ {

	/** The cwpmt wkp hist PK. */
	public static volatile SingularAttribute<CwpmtWkpHist, CwpmtWkpHistPK> cwpmtWkpHistPK;
	
	/** The str D. */
	public static volatile SingularAttribute<CwpmtWkpHist, GeneralDate> strD;
	
	/** The end D. */
	public static volatile SingularAttribute<CwpmtWkpHist, GeneralDate> endD;

}