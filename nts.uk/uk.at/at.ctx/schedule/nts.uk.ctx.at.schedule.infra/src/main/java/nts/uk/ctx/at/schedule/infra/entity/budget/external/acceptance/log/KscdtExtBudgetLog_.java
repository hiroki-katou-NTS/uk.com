/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.budget.external.acceptance.log;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import nts.arc.time.GeneralDateTime;

/**
 * The Class KbldtExtBudgetLog_.
 */
@StaticMetamodel(KscdtExtBudgetLog.class)
public class KscdtExtBudgetLog_ {
    
    /** The exe id. */
    public static volatile SingularAttribute<KscdtExtBudgetLog, String> exeId;
    
    /** The str date time. */
    public static volatile SingularAttribute<KscdtExtBudgetLog, GeneralDateTime> strDateTime;
    
    /** The end date time. */
    public static volatile SingularAttribute<KscdtExtBudgetLog, GeneralDateTime> endDateTime;
    
    /** The sid. */
    public static volatile SingularAttribute<KscdtExtBudgetLog, String> sid;
    
    /** The completion atr. */
    public static volatile SingularAttribute<KscdtExtBudgetLog, Integer> completionAtr;
    
}
