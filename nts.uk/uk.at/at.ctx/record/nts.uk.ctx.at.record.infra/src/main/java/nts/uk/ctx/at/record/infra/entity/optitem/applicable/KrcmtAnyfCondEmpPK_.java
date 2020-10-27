/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.entity.optitem.applicable;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KrcmtAnyfCondEmpPK_.
 */
@StaticMetamodel(KrcmtAnyfCondEmpPK.class)
public class KrcmtAnyfCondEmpPK_ {

	/** The cid. */
	public static volatile SingularAttribute<KrcmtAnyfCondEmpPK, String> cid;

	/** The optional item no. */
	public static volatile SingularAttribute<KrcmtAnyfCondEmpPK, Integer> optionalItemNo;

	/** The emp cd. */
	public static volatile SingularAttribute<KrcmtAnyfCondEmpPK, String> empCd;

}
