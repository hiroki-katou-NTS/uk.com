package nts.uk.ctx.at.schedule.infra.entity.executionlog;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KscmtScheduleErrLogPK_.
 */
@StaticMetamodel(KscmtScheduleErrLogPK.class)
public class KscmtScheduleErrLogPK_ {

	/** The exe id. */
	public static volatile SingularAttribute<KscmtScheduleErrLogPK, String> exeId;

	/** The err content. */
	public static volatile SingularAttribute<KscmtScheduleErrLogPK, String> sid;
	
	/** The ymd. */
	public static volatile SingularAttribute<KscmtScheduleErrLogPK, String> ymd;

}
