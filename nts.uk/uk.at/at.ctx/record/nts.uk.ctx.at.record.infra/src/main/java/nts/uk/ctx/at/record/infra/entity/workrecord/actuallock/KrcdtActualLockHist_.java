/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.entity.workrecord.actuallock;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KrcdtActualLockHist_.
 */
@StaticMetamodel(KrcdtActualLockHist.class)
public class KrcdtActualLockHist_ {

	/** The krcmt actual lock PK. */
	public static volatile SingularAttribute<KrcdtActualLockHist, KrcdtActualLockHistPK> krcdtActualLockHistPK;
	
	/** The target month. */
	public static volatile SingularAttribute<KrcdtActualLockHist, Integer> targetMonth;
	
	/** The updator. */
	public static volatile SingularAttribute<KrcdtActualLockHist, String> updator;
	
	/** The d lock state. */
	public static volatile SingularAttribute<KrcdtActualLockHist, Integer> dLockState;
	
	/** The m lock state. */
	public static volatile SingularAttribute<KrcdtActualLockHist, Integer> mLockState;
}
