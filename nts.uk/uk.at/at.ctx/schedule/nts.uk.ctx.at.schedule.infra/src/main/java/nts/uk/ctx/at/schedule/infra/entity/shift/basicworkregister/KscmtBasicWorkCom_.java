/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.shift.basicworkregister;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KscmtBasicWorkCom_.
 */
@StaticMetamodel(KscmtBasicWorkCom.class)
public class KscmtBasicWorkCom_ {
	
	/** The kcbmt company work set PK. */
	public static volatile SingularAttribute<KscmtBasicWorkCom, KscmtBasicWorkComPK> kscmtBasicWorkComPK;
	
	/** The work type code. */
	public static volatile SingularAttribute<KscmtBasicWorkCom, String> worktypeCode;

	/** The working code. */
	public static volatile SingularAttribute<KscmtBasicWorkCom, String> workingCode;

}
