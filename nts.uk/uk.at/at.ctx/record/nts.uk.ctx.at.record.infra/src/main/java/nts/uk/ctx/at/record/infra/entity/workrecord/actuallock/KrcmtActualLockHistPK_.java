/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.entity.workrecord.actuallock;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import nts.arc.time.GeneralDateTime;

/**
 * The Class KrcmtActualLockHistPK_.
 */
@StaticMetamodel(KrcmtActualLockHistPK.class)
public class KrcmtActualLockHistPK_ {

	/** The cid. */
	public static volatile SingularAttribute<KrcmtActualLockHistPK, String> cid;
	
	/** The closure id. */
	public static volatile SingularAttribute<KrcmtActualLockHistPK, Integer> closureId;
	
	/** The lock date. */
	public static volatile SingularAttribute<KrcmtActualLockHistPK, GeneralDateTime> lockDate;
}
