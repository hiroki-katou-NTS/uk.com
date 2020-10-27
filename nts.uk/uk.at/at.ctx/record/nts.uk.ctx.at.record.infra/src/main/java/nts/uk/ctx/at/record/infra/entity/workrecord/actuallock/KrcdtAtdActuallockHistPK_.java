/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.entity.workrecord.actuallock;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import nts.arc.time.GeneralDateTime;

/**
 * The Class KrcdtAtdActuallockHistPK_.
 */
@StaticMetamodel(KrcdtAtdActuallockHistPK.class)
public class KrcdtAtdActuallockHistPK_ {

	/** The cid. */
	public static volatile SingularAttribute<KrcdtAtdActuallockHistPK, String> cid;
	
	/** The closure id. */
	public static volatile SingularAttribute<KrcdtAtdActuallockHistPK, Integer> closureId;
	
	/** The lock date. */
	public static volatile SingularAttribute<KrcdtAtdActuallockHistPK, GeneralDateTime> lockDate;
}
