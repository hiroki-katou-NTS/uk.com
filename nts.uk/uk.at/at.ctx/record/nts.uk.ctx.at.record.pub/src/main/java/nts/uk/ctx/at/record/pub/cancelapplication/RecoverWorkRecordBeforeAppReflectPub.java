package nts.uk.ctx.at.record.pub.cancelapplication;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.pub.appreflect.export.RCReflectStatusResultExport;
import nts.uk.ctx.at.shared.dom.scherec.application.common.ApplicationShare;
import nts.uk.shr.com.enumcommon.NotUseAtr;

public interface RecoverWorkRecordBeforeAppReflectPub {

	public RCRecoverAppReflectOutputExport process(ApplicationShare application, GeneralDate date,
			RCReflectStatusResultExport reflectStatus, NotUseAtr dbRegisterClassfi);
}
