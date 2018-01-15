/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.budget.external.actualresult.timeunit;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import nts.arc.time.GeneralDate;

/**
 * The Class KscdtExtBudgetTimePK_.
 */
@StaticMetamodel(KscdtExtBudgetTimePK.class)
public class KscdtExtBudgetTimePK_ {
    
    /** The wkpid. */
    public static volatile SingularAttribute<KscdtExtBudgetTimePK, String> wkpid;
    
    /** The actual date. */
    public static volatile SingularAttribute<KscdtExtBudgetTimePK, GeneralDate> actualDate;
    
    /** The ext budget cd. */
    public static volatile SingularAttribute<KscdtExtBudgetTimePK, String> extBudgetCd;
    
    /** The period time no. */
    public static volatile SingularAttribute<KscdtExtBudgetTimePK, Integer> periodTimeNo;
}
