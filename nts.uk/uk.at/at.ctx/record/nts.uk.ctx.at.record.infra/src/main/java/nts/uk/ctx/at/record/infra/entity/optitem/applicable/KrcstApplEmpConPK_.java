/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.entity.optitem.applicable;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KrcstApplEmpConPK_.
 */
@StaticMetamodel(KrcstApplEmpConPK.class)
public class KrcstApplEmpConPK_ {

	/** The cid. */
	public static volatile SingularAttribute<KrcstApplEmpConPK, String> cid;

	/** The optional item no. */
	public static volatile SingularAttribute<KrcstApplEmpConPK, Integer> optionalItemNo;

	/** The emp cd. */
	public static volatile SingularAttribute<KrcstApplEmpConPK, String> empCd;

}
