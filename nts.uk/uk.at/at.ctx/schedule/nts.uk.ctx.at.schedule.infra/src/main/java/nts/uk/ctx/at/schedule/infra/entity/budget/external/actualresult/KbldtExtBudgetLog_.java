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
@StaticMetamodel(KbldtExtBudgetLog.class)
public class KbldtExtBudgetLog_ {
    
    /** The cid. */
    public static volatile SingularAttribute<KbldtExtBudgetLog, String> cid;
    
    /** The exe id. */
    public static volatile SingularAttribute<KbldtExtBudgetLog, String> exeId;
    
    /** The str D. */
    public static volatile SingularAttribute<KbldtExtBudgetLog, GeneralDate> strD;
    
    /** The end D. */
    public static volatile SingularAttribute<KbldtExtBudgetLog, GeneralDate> endD;
    
    /** The sid. */
    public static volatile SingularAttribute<KbldtExtBudgetLog, String> sid;
    
    /** The completion atr. */
    public static volatile SingularAttribute<KbldtExtBudgetLog, Integer> completionAtr;
    
}
