/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.employment.statutory.worktime.company;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class JcwtstCompanyWtSetPK_.
 */
@StaticMetamodel(JcwtstCompanyWtSetPK.class)
public class JcwtstCompanyWtSetPK_ {

	/** The cid. */
	public static volatile SingularAttribute<JcwtstCompanyWtSetPK, String> cid;

	/** The y K. */
	public static volatile SingularAttribute<JcwtstCompanyWtSetPK, Integer> yK;

	/** The ctg. */
	public static volatile SingularAttribute<JcwtstCompanyWtSetPK, Integer> ctg;

	/** The type. */
	public static volatile SingularAttribute<JcwtstCompanyWtSet, Integer> type;

}
