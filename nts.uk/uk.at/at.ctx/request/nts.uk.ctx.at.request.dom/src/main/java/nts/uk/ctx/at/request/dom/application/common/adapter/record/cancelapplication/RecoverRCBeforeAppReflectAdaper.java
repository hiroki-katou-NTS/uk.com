package nts.uk.ctx.at.request.dom.application.common.adapter.record.cancelapplication;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.applicationreflect.object.ReflectStatusResult;
import nts.uk.ctx.at.shared.dom.scherec.application.common.ApplicationShare;
import nts.uk.shr.com.enumcommon.NotUseAtr;

public interface RecoverRCBeforeAppReflectAdaper {

	public RQRecoverAppReflectOutputImport process(ApplicationShare application, GeneralDate date,
			ReflectStatusResult reflectStatus, NotUseAtr dbRegisterClassfi);
}
