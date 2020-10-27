/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.entity.workrecord.actuallock;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;


/**
 * The Class KrcdtAtdActualLockPK_.
 */
@StaticMetamodel(KrcdtAtdActualLockPK.class)
public class KrcdtAtdActualLockPK_ {

	/** The cid. */
	public static volatile SingularAttribute<KrcdtAtdActualLockPK, String> cid;
	
	/** The closure id. */
	public static volatile SingularAttribute<KrcdtAtdActualLockPK, Integer> closureId;
}
