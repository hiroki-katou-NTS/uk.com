/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.request.infra.entity.valication.history;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import nts.arc.time.GeneralDate;

/**
 * The Class KrqmtVacationHistory_.
 */
@StaticMetamodel(KrqmtVacationHistory.class)
public class KrqmtVacationHistory_ {
	
	/** The krqmt vacation history PK. */
	public static volatile SingularAttribute<KrqmtVacationHistory, KrqmtVacationHistoryPK> krqmtVacationHistoryPK;
	
	/** The start date. */
	public static volatile SingularAttribute<KrqmtVacationHistory, GeneralDate> startDate;
	
	/** The max day. */
	public static volatile SingularAttribute<KrqmtVacationHistory, Integer> maxDay;
	
	/** The end date. */
	public static volatile SingularAttribute<KrqmtVacationHistory, GeneralDate> endDate;
	
}
