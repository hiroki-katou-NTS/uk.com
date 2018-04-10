/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.executionlog;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

/**
 * The Class KscdtScheExeLog_.
 */
@StaticMetamodel(KscdtScheExeLog.class)
public class KscdtScheExeLog_ {

	/** The kscdt sche exe log PK. */
	public static volatile SingularAttribute<KscdtScheExeLog, KscdtScheExeLogPK> kscdtScheExeLogPK;

	/** The exe str D. */
	public static volatile SingularAttribute<KscdtScheExeLog, GeneralDateTime> exeStrD;

	/** The exe end D. */
	public static volatile SingularAttribute<KscdtScheExeLog, GeneralDateTime> exeEndD;

	/** The exe sid. */
	public static volatile SingularAttribute<KscdtScheExeLog, String> exeSid;

	/** The start ymd. */
	public static volatile SingularAttribute<KscdtScheExeLog, GeneralDate> startYmd;

	/** The end ymd. */
	public static volatile SingularAttribute<KscdtScheExeLog, GeneralDate> endYmd;

	/** The completion status. */
	public static volatile SingularAttribute<KscdtScheExeLog, Integer> completionStatus;

	/** The execution attribute. */
	public static volatile SingularAttribute<KscdtScheExeLog, Integer> exeAtr;
}
