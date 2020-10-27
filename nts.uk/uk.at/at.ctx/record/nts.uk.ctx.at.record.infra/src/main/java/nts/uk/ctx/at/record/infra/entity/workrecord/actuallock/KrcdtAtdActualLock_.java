/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.entity.workrecord.actuallock;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KrcdtAtdActualLock_.
 */
@StaticMetamodel(KrcdtAtdActualLock.class)
public class KrcdtAtdActualLock_ {

	/** The krcmt actual lock PK. */
	public static volatile SingularAttribute<KrcdtAtdActualLock, KrcdtAtdActualLockPK> krcdtAtdActualLockPK;
	
	/** The d lock state. */
	public static volatile SingularAttribute<KrcdtAtdActualLock, Integer> dLockState;
	
	/** The m lock state. */
	public static volatile SingularAttribute<KrcdtAtdActualLock, Integer> mLockState;
}
