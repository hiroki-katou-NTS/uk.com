/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.executionlog;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KscmtScheduleExcLogPK_.
 */
@StaticMetamodel(KscmtScheduleExcLogPK.class)
public class KscmtScheduleExcLogPK_ {

	 /** The cid. */
    public static volatile SingularAttribute<KscmtScheduleExcLogPK, String> cid;
    
    /** The patternCd. */
    public static volatile SingularAttribute<KscmtScheduleExcLogPK, String> exeId;
	
}
