/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.executionlog;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

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
    public static volatile SingularAttribute<KscmtScheduleExcLog, Integer> startYmd;
    
    /** The end ymd. */
    public static volatile SingularAttribute<KscmtScheduleExcLog, Integer> endYmd;
    
    /** The completion status. */
    public static volatile SingularAttribute<KscmtScheduleExcLog, Integer> completionStatus;
    
    /** The copy start ymd. */
    public static volatile SingularAttribute<KscmtScheduleExcLog, Integer> copyStartYmd;
    
    /** The create method atr. */
    public static volatile SingularAttribute<KscmtScheduleExcLog, Integer> createMethodAtr;
    
    /** The confirm. */
    public static volatile SingularAttribute<KscmtScheduleExcLog, Integer> confirm;
    
    /** The implement atr. */
    public static volatile SingularAttribute<KscmtScheduleExcLog, Integer> implementAtr;
    
    /** The re create atr. */
    public static volatile SingularAttribute<KscmtScheduleExcLog, Integer> reCreateAtr;
    
    /** The process exe atr. */
    public static volatile SingularAttribute<KscmtScheduleExcLog, Integer> processExeAtr;
    
    /** The re master info. */
    public static volatile SingularAttribute<KscmtScheduleExcLog, Integer> reMasterInfo;
    
    /** The re abst hd busines. */
    public static volatile SingularAttribute<KscmtScheduleExcLog, Integer> reAbstHdBusines;
    
    /** The re working hours. */
    public static volatile SingularAttribute<KscmtScheduleExcLog, Integer> reWorkingHours;
    
    /** The re time assignment. */
    public static volatile SingularAttribute<KscmtScheduleExcLog, Integer> reTimeAssignment;
    
    /** The re dir line bounce. */
    public static volatile SingularAttribute<KscmtScheduleExcLog, Integer> reDirLineBounce;
    
    /** The re time child care. */
    public static volatile SingularAttribute<KscmtScheduleExcLog, Integer> reTimeChildCare;
}
