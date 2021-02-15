/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.shift.basicworkregister;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KscmtWorkplaceWorkSet_.
 */
@StaticMetamodel(KscmtWorkplaceWorkSet.class)
public class KscmtWorkplaceWorkSet_ {

	/** The kwbmt workplace work set PK. */
	public static volatile SingularAttribute<KscmtWorkplaceWorkSet, KscmtWorkplaceWorkSetPK> kscmtWorkplaceWorkSetPK;
	
	/** The workype code. */
	public static volatile SingularAttribute<KscmtWorkplaceWorkSet, String> workypeCode;

	/** The working code. */
	public static volatile SingularAttribute<KscmtWorkplaceWorkSet, String> workingCode;

	
}
