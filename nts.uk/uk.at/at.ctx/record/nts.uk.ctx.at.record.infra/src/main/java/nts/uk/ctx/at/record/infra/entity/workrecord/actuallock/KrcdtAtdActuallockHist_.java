/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.entity.workrecord.actuallock;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KrcdtAtdActuallockHist_.
 */
@StaticMetamodel(KrcdtAtdActuallockHist.class)
public class KrcdtAtdActuallockHist_ {

	/** The krcmt actual lock PK. */
	public static volatile SingularAttribute<KrcdtAtdActuallockHist, KrcdtAtdActuallockHistPK> krcdtAtdActuallockHistPK;
	
	/** The target month. */
	public static volatile SingularAttribute<KrcdtAtdActuallockHist, Integer> targetMonth;
	
	/** The updator. */
	public static volatile SingularAttribute<KrcdtAtdActuallockHist, String> updator;
	
	/** The d lock state. */
	public static volatile SingularAttribute<KrcdtAtdActuallockHist, Integer> dLockState;
	
	/** The m lock state. */
	public static volatile SingularAttribute<KrcdtAtdActuallockHist, Integer> mLockState;
}
