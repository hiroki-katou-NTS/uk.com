/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.statutory.worktime.company;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class JcwtstCompanyWtSetPK_.
 */
@StaticMetamodel(KcwstCompanyWtSetPK.class)
public class KcwstCompanyWtSetPK_ {

	/** The cid. */
	public static volatile SingularAttribute<KcwstCompanyWtSetPK, String> cid;

	/** The y K. */
	public static volatile SingularAttribute<KcwstCompanyWtSetPK, Integer> yK;

	/** The ctg. */
	public static volatile SingularAttribute<KcwstCompanyWtSetPK, Integer> ctg;

	/** The type. */
	public static volatile SingularAttribute<KcwstCompanyWtSetPK, Integer> type;

}
