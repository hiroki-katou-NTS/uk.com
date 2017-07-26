/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.shift.basicworkregister;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KcbmtCompanyWorkSet_.
 */
@StaticMetamodel(KcbmtCompanyWorkSet.class)
public class KcbmtCompanyWorkSet_ {
	
	/** The kcbmt company work set PK. */
	public static volatile SingularAttribute<KcbmtCompanyWorkSet, KcbmtCompanyWorkSetPK> kcbmtCompanyWorkSetPK;
	
	/** The work type code. */
	public static volatile SingularAttribute<KcbmtCompanyWorkSet, String> worktypeCode;

	/** The working code. */
	public static volatile SingularAttribute<KcbmtCompanyWorkSet, String> workingCode;

}
