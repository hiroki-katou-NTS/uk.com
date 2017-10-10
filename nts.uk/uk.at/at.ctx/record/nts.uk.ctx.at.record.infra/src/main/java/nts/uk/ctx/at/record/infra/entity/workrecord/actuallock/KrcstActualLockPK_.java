/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.entity.workrecord.actuallock;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;


/**
 * The Class KrcstActualLockPK_.
 */
@StaticMetamodel(KrcstActualLockPK.class)
public class KrcstActualLockPK_ {

	/** The cid. */
	public static volatile SingularAttribute<KrcstActualLockPK, String> cid;
	
	/** The closure id. */
	public static volatile SingularAttribute<KrcstActualLockPK, Integer> closureId;
}
