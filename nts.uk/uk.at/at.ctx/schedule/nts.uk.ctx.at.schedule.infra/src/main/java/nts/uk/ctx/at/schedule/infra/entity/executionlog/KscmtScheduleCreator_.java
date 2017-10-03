/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.executionlog;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KscmtScheduleCreator_.
 */
@StaticMetamodel(KscmtScheduleCreator.class)
public class KscmtScheduleCreator_ {
	
	/** The kscmt schedule creator PK. */
    public static volatile SingularAttribute<KscmtScheduleCreator, KscmtScheduleCreatorPK> kscmtScheduleCreatorPK;
    
    /** The exe status. */
    public static volatile SingularAttribute<KscmtScheduleCreator, Integer> exeStatus;
    
}
