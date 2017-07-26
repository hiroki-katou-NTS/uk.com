/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.shift.basicworkregister;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KwbmtWorkplaceWorkSet_.
 */
@StaticMetamodel(KwbmtWorkplaceWorkSet.class)
public class KwbmtWorkplaceWorkSet_ {

	/** The kwbmt workplace work set PK. */
	public static volatile SingularAttribute<KwbmtWorkplaceWorkSet, KwbmtWorkplaceWorkSetPK> kwbmtWorkplaceWorkSetPK;
	
	/** The workype code. */
	public static volatile SingularAttribute<KwbmtWorkplaceWorkSet, String> workypeCode;

	/** The working code. */
	public static volatile SingularAttribute<KwbmtWorkplaceWorkSet, String> workingCode;

	
}
