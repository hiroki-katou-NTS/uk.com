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

	/** The workplace id. */
	public static volatile SingularAttribute<KwbmtWorkplaceWorkSet, String> workplaceId;
	
	/** The wd work type code. */
	public static volatile SingularAttribute<KwbmtWorkplaceWorkSet, String> wdWorkTypeCode;

	/** The wd working code. */
	public static volatile SingularAttribute<KwbmtWorkplaceWorkSet, String> wdWorkingCode;

	/** The nwd law work type code. */
	public static volatile SingularAttribute<KwbmtWorkplaceWorkSet, String> nwdLawWorkTypeCode;

	/** The nwd law working code. */
	public static volatile SingularAttribute<KwbmtWorkplaceWorkSet, String> nwdLawWorkingCode;

	/** The nwd ext work type code. */
	public static volatile SingularAttribute<KwbmtWorkplaceWorkSet, String> nwdExtWorkTypeCode;

	/** The nwd ext working code. */
	public static volatile SingularAttribute<KwbmtWorkplaceWorkSet, String> nwdExtWorkingCode;
}
