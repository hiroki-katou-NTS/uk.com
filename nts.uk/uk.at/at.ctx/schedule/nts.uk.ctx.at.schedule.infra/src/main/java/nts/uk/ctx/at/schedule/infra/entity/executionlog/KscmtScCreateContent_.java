/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.executionlog;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KscmtScCreateContent_.
 */
@StaticMetamodel(KscmtScCreateContent.class)
public class KscmtScCreateContent_ {
	
    /** The exe id. */
    public static volatile SingularAttribute<KscmtScCreateContent, String> exeId;
    
    /** The copy start ymd. */
    public static volatile SingularAttribute<KscmtScCreateContent, Integer> copyStartYmd;
    
    /** The create method atr. */
    public static volatile SingularAttribute<KscmtScCreateContent, Integer> createMethodAtr;
    
    /** The confirm. */
    public static volatile SingularAttribute<KscmtScCreateContent, Integer> confirm;
    
    /** The implement atr. */
    public static volatile SingularAttribute<KscmtScCreateContent, Integer> implementAtr;
    
    /** The re create atr. */
    public static volatile SingularAttribute<KscmtScCreateContent, Integer> reCreateAtr;
    
    /** The process exe atr. */
    public static volatile SingularAttribute<KscmtScCreateContent, Integer> processExeAtr;
    
    /** The re master info. */
    public static volatile SingularAttribute<KscmtScCreateContent, Integer> reMasterInfo;
    
    /** The re abst hd busines. */
    public static volatile SingularAttribute<KscmtScCreateContent, Integer> reAbstHdBusines;
    
    /** The re working hours. */
    public static volatile SingularAttribute<KscmtScCreateContent, Integer> reWorkingHours;
    
    /** The re time assignment. */
    public static volatile SingularAttribute<KscmtScCreateContent, Integer> reTimeAssignment;
    
    /** The re dir line bounce. */
    public static volatile SingularAttribute<KscmtScCreateContent, Integer> reDirLineBounce;
    
    /** The re time child care. */
    public static volatile SingularAttribute<KscmtScCreateContent, Integer> reTimeChildCare;
}
