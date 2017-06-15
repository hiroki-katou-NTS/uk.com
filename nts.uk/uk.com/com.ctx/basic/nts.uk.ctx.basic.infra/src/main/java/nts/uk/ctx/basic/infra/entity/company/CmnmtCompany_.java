/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.entity.company;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KclmtClosure_.
 */
@StaticMetamodel(CmnmtCompany.class)
public class CmnmtCompany_ {

	/** The ccd. */
	public static volatile SingularAttribute<CmnmtCompany, String> ccd;

	/** The cid. */
	public static volatile SingularAttribute<CmnmtCompany, String> cid;

	/** The str M. */
	public static volatile SingularAttribute<CmnmtCompany, Integer> strM;


}