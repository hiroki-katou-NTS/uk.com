package nts.uk.ctx.at.schedule.infra.entity.executionlog;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KscmtScheduleErrLog_.
 */
@StaticMetamodel(KscmtScheduleErrLog.class)
public class KscmtScheduleErrLog_ {

	/** The kscmt schedule err log PK. */
	public static volatile SingularAttribute<KscmtScheduleErrLog, KscmtScheduleErrLogPK> kscmtScheduleErrLogPK;

	/** The err content. */
	public static volatile SingularAttribute<KscmtScheduleErrLog, String> errContent;

}
