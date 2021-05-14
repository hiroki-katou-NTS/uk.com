package nts.uk.ctx.at.schedule.pub.appreflectprocess.appremove;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.pub.appreflectprocess.export.SCReflectStatusResultExport;
import nts.uk.shr.com.enumcommon.NotUseAtr;

public interface RecoverSCBeforeAppReflectPub {

	public SCRecoverAppReflectExport process(Object application, GeneralDate date, SCReflectStatusResultExport reflectStatus,
			NotUseAtr dbRegisterClassfi);
}
