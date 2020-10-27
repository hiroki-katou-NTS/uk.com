/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.shift.estimate.commonset;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KscmtEstAlarmColorPK_.
 */
@StaticMetamodel(KscmtEstAlarmColorPK.class)
public class KscmtEstAlarmColorPK_ {

	/** The cid. */
	public static volatile SingularAttribute<KscmtEstAlarmColorPK, String> cid;
	
	/** The estimate condition. */
	public static volatile SingularAttribute<KscmtEstAlarmColorPK, Integer> estimateCondition;
}
