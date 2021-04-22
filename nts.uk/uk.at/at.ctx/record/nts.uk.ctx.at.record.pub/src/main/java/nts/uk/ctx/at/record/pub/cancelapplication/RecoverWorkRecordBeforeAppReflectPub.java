package nts.uk.ctx.at.record.pub.cancelapplication;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.pub.cancelapplication.state.ReflectStatusResultExport;
import nts.uk.ctx.at.shared.dom.scherec.application.common.ApplicationShare;
import nts.uk.shr.com.enumcommon.NotUseAtr;

public interface RecoverWorkRecordBeforeAppReflectPub {

	public RCRecoverAppReflectOutputExport process(ApplicationShare application, GeneralDate date,
			ReflectStatusResultExport reflectStatus, NotUseAtr dbRegisterClassfi);
}
