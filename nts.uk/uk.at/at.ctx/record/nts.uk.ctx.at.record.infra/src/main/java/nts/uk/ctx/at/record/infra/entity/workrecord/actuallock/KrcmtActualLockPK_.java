/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.entity.workrecord.actuallock;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;


/**
 * The Class KrcmtActualLockPK_.
 */
@StaticMetamodel(KrcmtActualLockPK.class)
public class KrcmtActualLockPK_ {

	/** The cid. */
	public static volatile SingularAttribute<KrcmtActualLockPK, String> cid;
	
	/** The closure id. */
	public static volatile SingularAttribute<KrcmtActualLockPK, Integer> closureId;
}
