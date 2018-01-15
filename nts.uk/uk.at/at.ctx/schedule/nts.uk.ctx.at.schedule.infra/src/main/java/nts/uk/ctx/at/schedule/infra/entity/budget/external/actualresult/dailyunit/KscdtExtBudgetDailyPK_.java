/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.budget.external.actualresult.dailyunit;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import nts.arc.time.GeneralDate;

/**
 * The Class KscdtExtBudgetDailyPK_.
 */
@StaticMetamodel(KscdtExtBudgetDailyPK.class)
public class KscdtExtBudgetDailyPK_ {
    
    /** The wkpid. */
    public static volatile SingularAttribute<KscdtExtBudgetDailyPK, String> wkpid;
    
    /** The actual date. */
    public static volatile SingularAttribute<KscdtExtBudgetDailyPK, GeneralDate> actualDate;
    
    /** The ext budget cd. */
    public static volatile SingularAttribute<KscdtExtBudgetDailyPK, String> extBudgetCd;
}
