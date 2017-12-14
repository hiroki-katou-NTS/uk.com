/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.shift.estimate.commonset;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KscstEstAlarmColorPK_.
 */
@StaticMetamodel(KscstEstAlarmColorPK.class)
public class KscstEstAlarmColorPK_ {

	/** The cid. */
	public static volatile SingularAttribute<KscstEstAlarmColorPK, String> cid;
	
	/** The estimate condition. */
	public static volatile SingularAttribute<KscstEstAlarmColorPK, Integer> estimateCondition;
}
