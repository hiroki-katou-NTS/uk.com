/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.statutory.worktime.employment;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class JewtstEmploymentWtSetPK_.
 */
@StaticMetamodel(KewstEmploymentWtSetPK.class)
public class KewstEmploymentWtSetPK_ {

	/** The cid. */
	public static volatile SingularAttribute<KewstEmploymentWtSetPK, String> cid;

	/** The y K. */
	public static volatile SingularAttribute<KewstEmploymentWtSetPK, Integer> yK;

	/** The ctg. */
	public static volatile SingularAttribute<KewstEmploymentWtSetPK, Integer> ctg;

	/** The type. */
	public static volatile SingularAttribute<KewstEmploymentWtSetPK, Integer> type;

	/** The empt cd. */
	public static volatile SingularAttribute<KewstEmploymentWtSetPK, String> emptCd;

}
