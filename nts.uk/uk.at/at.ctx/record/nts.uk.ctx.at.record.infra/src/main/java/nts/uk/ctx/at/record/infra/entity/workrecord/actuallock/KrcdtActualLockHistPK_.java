/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.entity.workrecord.actuallock;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import nts.arc.time.GeneralDateTime;

/**
 * The Class KrcdtActualLockHistPK_.
 */
@StaticMetamodel(KrcdtActualLockHistPK.class)
public class KrcdtActualLockHistPK_ {

	/** The cid. */
	public static volatile SingularAttribute<KrcdtActualLockHistPK, String> cid;
	
	/** The closure id. */
	public static volatile SingularAttribute<KrcdtActualLockHistPK, Integer> closureId;
	
	/** The lock date. */
	public static volatile SingularAttribute<KrcdtActualLockHistPK, GeneralDateTime> lockDate;
}
