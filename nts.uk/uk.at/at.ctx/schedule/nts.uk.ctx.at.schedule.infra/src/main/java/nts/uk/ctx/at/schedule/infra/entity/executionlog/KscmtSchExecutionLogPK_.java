/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.executionlog;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KscmtSchExecutionLogPK_.
 */
@StaticMetamodel(KscmtSchExecutionLogPK.class)
public class KscmtSchExecutionLogPK_ {

	 /** The cid. */
    public static volatile SingularAttribute<KscmtSchExecutionLogPK, String> cid;
    
    /** The exe id. */
    public static volatile SingularAttribute<KscmtSchExecutionLogPK, String> exeId;
	
}
