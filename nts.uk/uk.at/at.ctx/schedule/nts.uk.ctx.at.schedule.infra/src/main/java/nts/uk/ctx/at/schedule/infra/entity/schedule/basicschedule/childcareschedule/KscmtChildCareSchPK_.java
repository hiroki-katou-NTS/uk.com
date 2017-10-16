/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.schedule.basicschedule.childcareschedule;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import nts.arc.time.GeneralDate;

/**
 * The Class KscmtChildCareSchPK_.
 */
@StaticMetamodel(KscmtChildCareSchPK.class)
public class KscmtChildCareSchPK_ {

	/** The sid. */
	public static volatile SingularAttribute<KscmtChildCareSchPK, String> sid;

	/** The ymd. */
	public static volatile SingularAttribute<KscmtChildCareSchPK, GeneralDate> ymd;

	/** The child care number. */
	public static volatile SingularAttribute<KscmtChildCareSchPK, Integer> childCareNumber;

}
