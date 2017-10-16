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
 * The Class KscmtSchExecutionLog_.
 */
@StaticMetamodel(KscmtSchExecutionLog.class)
public class KscmtSchExecutionLog_ {
	
	/** The kscmt sch execution log PK. */
    public static volatile SingularAttribute<KscmtSchExecutionLog, KscmtSchExecutionLogPK> kscmtSchExecutionLogPK;
    
    /** The exe str D. */
    public static volatile SingularAttribute<KscmtSchExecutionLog, GeneralDateTime> exeStrD;
    
    /** The exe end D. */
    public static volatile SingularAttribute<KscmtSchExecutionLog, GeneralDateTime> exeEndD;
    
    /** The exe sid. */
    public static volatile SingularAttribute<KscmtSchExecutionLog, String> exeSid;
    
    /** The start ymd. */
    public static volatile SingularAttribute<KscmtSchExecutionLog, GeneralDate> startYmd;
    
    /** The end ymd. */
    public static volatile SingularAttribute<KscmtSchExecutionLog, GeneralDate> endYmd;
    
    /** The completion status. */
    public static volatile SingularAttribute<KscmtSchExecutionLog, Integer> completionStatus;
}
