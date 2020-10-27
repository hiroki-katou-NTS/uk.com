/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.shift.basicworkregister;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KscmtBasicWorkWkp_.
 */
@StaticMetamodel(KscmtBasicWorkWkp.class)
public class KscmtBasicWorkWkp_ {

	/** The kwbmt workplace work set PK. */
	public static volatile SingularAttribute<KscmtBasicWorkWkp, KscmtBasicWorkWkpPK> kscmtBasicWorkWkpPK;
	
	/** The workype code. */
	public static volatile SingularAttribute<KscmtBasicWorkWkp, String> workypeCode;

	/** The working code. */
	public static volatile SingularAttribute<KscmtBasicWorkWkp, String> workingCode;

	
}
