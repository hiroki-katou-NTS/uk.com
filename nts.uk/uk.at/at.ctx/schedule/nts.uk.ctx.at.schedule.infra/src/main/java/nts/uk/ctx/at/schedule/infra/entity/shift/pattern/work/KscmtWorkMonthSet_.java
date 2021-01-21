/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.shift.pattern.work;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KwmmtWorkMonthSet_.
 */
@StaticMetamodel(KscmtWorkMonthSet.class)
public class KscmtWorkMonthSet_ {

	/** The kscmt work month set PK. */
	public static volatile SingularAttribute<KscmtWorkMonthSet, KscmtWorkMonthSetPK> kscmtWorkMonthSetPK;
	
	/** The work type cd. */
	public static volatile SingularAttribute<KscmtWorkMonthSet, String> workTypeCd;
	
	/** The working cd. */
	public static volatile SingularAttribute<KscmtWorkMonthSet, String> workingCd;

	/** The contract cd. */
	public static volatile SingularAttribute<KscmtWorkMonthSet, String> contractCd;
	
}