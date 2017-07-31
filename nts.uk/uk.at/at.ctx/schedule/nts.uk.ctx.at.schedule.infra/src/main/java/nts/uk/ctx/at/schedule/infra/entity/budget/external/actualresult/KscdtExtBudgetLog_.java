/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.budget.external.actualresult;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import nts.arc.time.GeneralDate;

/**
 * The Class KbldtExtBudgetLog_.
 */
@StaticMetamodel(KscdtExtBudgetLog.class)
public class KscdtExtBudgetLog_ {
    
    /** The exe id. */
    public static volatile SingularAttribute<KscdtExtBudgetLog, String> exeId;
    
    /** The str D. */
    public static volatile SingularAttribute<KscdtExtBudgetLog, GeneralDate> strD;
    
    /** The end D. */
    public static volatile SingularAttribute<KscdtExtBudgetLog, GeneralDate> endD;
    
    /** The sid. */
    public static volatile SingularAttribute<KscdtExtBudgetLog, String> sid;
    
    /** The completion atr. */
    public static volatile SingularAttribute<KscdtExtBudgetLog, Integer> completionAtr;
    
}
