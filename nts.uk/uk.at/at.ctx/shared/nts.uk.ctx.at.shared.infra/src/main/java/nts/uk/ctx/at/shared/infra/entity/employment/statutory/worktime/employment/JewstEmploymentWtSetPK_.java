/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.employment.statutory.worktime.employment;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class JewtstEmploymentWtSetPK_.
 */
@StaticMetamodel(JewstEmploymentWtSetPK.class)
public class JewstEmploymentWtSetPK_ {

	/** The cid. */
	public static volatile SingularAttribute<JewstEmploymentWtSetPK, String> cid;

	/** The y K. */
	public static volatile SingularAttribute<JewstEmploymentWtSetPK, Integer> yK;

	/** The ctg. */
	public static volatile SingularAttribute<JewstEmploymentWtSetPK, Integer> ctg;

	/** The type. */
	public static volatile SingularAttribute<JewstEmploymentWtSetPK, Integer> type;

	/** The empt cd. */
	public static volatile SingularAttribute<JewstEmploymentWtSetPK, String> emptCd;

}
