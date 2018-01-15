/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.budget.external.actualresult.error;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KscdtExtBudgetErrorPK_.
 */
@StaticMetamodel(KscdtExtBudgetErrorPK.class)
public class KscdtExtBudgetErrorPK_ {
    
    /** The exe id. */
    public static volatile SingularAttribute<KscdtExtBudgetErrorPK, String> exeId;
    
    /** The line no. */
    public static volatile SingularAttribute<KscdtExtBudgetErrorPK, Integer> lineNo;
    
    /** The column no. */
    public static volatile SingularAttribute<KscdtExtBudgetErrorPK, Integer> columnNo;
}
