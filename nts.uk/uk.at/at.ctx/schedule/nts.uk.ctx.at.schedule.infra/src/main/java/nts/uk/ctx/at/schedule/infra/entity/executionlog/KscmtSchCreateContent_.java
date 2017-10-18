/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.executionlog;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KscmtSchCreateContent_.
 */
@StaticMetamodel(KscmtSchCreateContent.class)
public class KscmtSchCreateContent_ {
	
    /** The exe id. */
    public static volatile SingularAttribute<KscmtSchCreateContent, String> exeId;
    
    /** The copy start ymd. */
    public static volatile SingularAttribute<KscmtSchCreateContent, Integer> copyStartYmd;
    
    /** The create method atr. */
    public static volatile SingularAttribute<KscmtSchCreateContent, Integer> createMethodAtr;
    
    /** The confirm. */
    public static volatile SingularAttribute<KscmtSchCreateContent, Integer> confirm;
    
    /** The implement atr. */
    public static volatile SingularAttribute<KscmtSchCreateContent, Integer> implementAtr;
    
    /** The re create atr. */
    public static volatile SingularAttribute<KscmtSchCreateContent, Integer> reCreateAtr;
    
    /** The process exe atr. */
    public static volatile SingularAttribute<KscmtSchCreateContent, Integer> processExeAtr;
    
    /** The re master info. */
    public static volatile SingularAttribute<KscmtSchCreateContent, Integer> reMasterInfo;
    
    /** The re abst hd busines. */
    public static volatile SingularAttribute<KscmtSchCreateContent, Integer> reAbstHdBusines;
    
    /** The re working hours. */
    public static volatile SingularAttribute<KscmtSchCreateContent, Integer> reWorkingHours;
    
    /** The re time assignment. */
    public static volatile SingularAttribute<KscmtSchCreateContent, Integer> reTimeAssignment;
    
    /** The re dir line bounce. */
    public static volatile SingularAttribute<KscmtSchCreateContent, Integer> reDirLineBounce;
    
    /** The re time child care. */
    public static volatile SingularAttribute<KscmtSchCreateContent, Integer> reTimeChildCare;
}
