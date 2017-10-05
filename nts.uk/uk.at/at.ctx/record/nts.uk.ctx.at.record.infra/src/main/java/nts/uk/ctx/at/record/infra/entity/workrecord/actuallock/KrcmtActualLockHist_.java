/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.entity.workrecord.actuallock;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KrcmtActualLockHist_.
 */
@StaticMetamodel(KrcmtActualLockHist.class)
public class KrcmtActualLockHist_ {

	/** The krcmt actual lock PK. */
	public static volatile SingularAttribute<KrcmtActualLockHist, KrcmtActualLockHistPK> krcmtActualLockPK;
	
	/** The target month. */
	public static volatile SingularAttribute<KrcmtActualLockHist, Integer> targetMonth;
	
	/** The updator. */
	public static volatile SingularAttribute<KrcmtActualLockHist, String> updator;
	
	/** The d lock state. */
	public static volatile SingularAttribute<KrcmtActualLockHist, Integer> dLockState;
	
	/** The m lock state. */
	public static volatile SingularAttribute<KrcmtActualLockHist, Integer> mLockState;
}
