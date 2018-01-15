/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.schedule.basicschedule.childcareschedule;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import nts.arc.time.GeneralDate;

/**
 * The Class KscdtScheChildCarePK_.
 */
@StaticMetamodel(KscdtScheChildCarePK.class)
public class KscdtScheChildCarePK_ {

	/** The sid. */
	public static volatile SingularAttribute<KscdtScheChildCarePK, String> sid;

	/** The ymd. */
	public static volatile SingularAttribute<KscdtScheChildCarePK, GeneralDate> ymd;

	/** The child care number. */
	public static volatile SingularAttribute<KscdtScheChildCarePK, Integer> childCareNumber;

}
