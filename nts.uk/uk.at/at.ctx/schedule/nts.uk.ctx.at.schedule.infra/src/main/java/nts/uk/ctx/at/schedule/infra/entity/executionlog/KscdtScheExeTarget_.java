/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.executionlog;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KscdtScheExeTarget_.
 */
@StaticMetamodel(KscdtScheExeTarget.class)
public class KscdtScheExeTarget_ {
	
	/** The kscmt sch creator PK. */
    public static volatile SingularAttribute<KscdtScheExeTarget, KscdtScheExeTargetPK> kscmtSchCreatorPK;
    
    /** The exe status. */
    public static volatile SingularAttribute<KscdtScheExeTarget, Integer> exeStatus;
    
}
