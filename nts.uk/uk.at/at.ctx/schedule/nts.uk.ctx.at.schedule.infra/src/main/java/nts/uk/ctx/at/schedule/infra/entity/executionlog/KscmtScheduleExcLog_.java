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
 * The Class KscmtScheduleExcLog_.
 */
@StaticMetamodel(KscmtScheduleExcLog.class)
public class KscmtScheduleExcLog_ {
	
	/** The kscmt schedule exc log PK. */
    public static volatile SingularAttribute<KscmtScheduleExcLog, KscmtScheduleExcLogPK> kscmtScheduleExcLogPK;
    
    /** The exe str D. */
    public static volatile SingularAttribute<KscmtScheduleExcLog, GeneralDateTime> exeStrD;
    
    /** The exe end D. */
    public static volatile SingularAttribute<KscmtScheduleExcLog, GeneralDateTime> exeEndD;
    
    /** The exe sid. */
    public static volatile SingularAttribute<KscmtScheduleExcLog, String> exeSid;
    
    /** The start ymd. */
    public static volatile SingularAttribute<KscmtScheduleExcLog, GeneralDate> startYmd;
    
    /** The end ymd. */
    public static volatile SingularAttribute<KscmtScheduleExcLog, GeneralDate> endYmd;
    
    /** The completion status. */
    public static volatile SingularAttribute<KscmtScheduleExcLog, Integer> completionStatus;
}
