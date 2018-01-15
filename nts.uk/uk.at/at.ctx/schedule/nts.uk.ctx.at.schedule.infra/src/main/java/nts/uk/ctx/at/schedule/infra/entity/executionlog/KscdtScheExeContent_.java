/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.executionlog;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KscdtScheExeContent_.
 */
@StaticMetamodel(KscdtScheExeContent.class)
public class KscdtScheExeContent_ {
	
    /** The exe id. */
    public static volatile SingularAttribute<KscdtScheExeContent, String> exeId;
    
    /** The copy start ymd. */
    public static volatile SingularAttribute<KscdtScheExeContent, Integer> copyStartYmd;
    
    /** The create method atr. */
    public static volatile SingularAttribute<KscdtScheExeContent, Integer> createMethodAtr;
    
    /** The confirm. */
    public static volatile SingularAttribute<KscdtScheExeContent, Integer> confirm;
    
    /** The implement atr. */
    public static volatile SingularAttribute<KscdtScheExeContent, Integer> implementAtr;
    
    /** The re create atr. */
    public static volatile SingularAttribute<KscdtScheExeContent, Integer> reCreateAtr;
    
    /** The process exe atr. */
    public static volatile SingularAttribute<KscdtScheExeContent, Integer> processExeAtr;
    
    /** The re master info. */
    public static volatile SingularAttribute<KscdtScheExeContent, Integer> reMasterInfo;
    
    /** The re abst hd busines. */
    public static volatile SingularAttribute<KscdtScheExeContent, Integer> reAbstHdBusines;
    
    /** The re working hours. */
    public static volatile SingularAttribute<KscdtScheExeContent, Integer> reWorkingHours;
    
    /** The re time assignment. */
    public static volatile SingularAttribute<KscdtScheExeContent, Integer> reTimeAssignment;
    
    /** The re dir line bounce. */
    public static volatile SingularAttribute<KscdtScheExeContent, Integer> reDirLineBounce;
    
    /** The re time child care. */
    public static volatile SingularAttribute<KscdtScheExeContent, Integer> reTimeChildCare;
}
