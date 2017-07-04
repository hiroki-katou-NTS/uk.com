/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.entity.company.beginningmonth;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class CbmstBeginningMonth_.
 */
@StaticMetamodel(CbmstBeginningMonth.class)
public class CbmstBeginningMonth_ {

	/** The cid. */
	public static volatile SingularAttribute<CbmstBeginningMonth, String> cid;

	/** The month. */
	public static volatile SingularAttribute<CbmstBeginningMonth, Integer> month;

}