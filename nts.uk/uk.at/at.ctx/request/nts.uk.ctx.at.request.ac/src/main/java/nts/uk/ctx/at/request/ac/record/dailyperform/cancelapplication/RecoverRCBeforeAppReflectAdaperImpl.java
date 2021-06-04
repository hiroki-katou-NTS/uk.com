package nts.uk.ctx.at.request.ac.record.dailyperform.cancelapplication;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.pub.appreflect.export.RCReasonNotReflectDailyExport;
import nts.uk.ctx.at.record.pub.appreflect.export.RCReasonNotReflectExport;
import nts.uk.ctx.at.record.pub.appreflect.export.RCReflectStatusResultExport;
import nts.uk.ctx.at.record.pub.appreflect.export.RCReflectedStateExport;
import nts.uk.ctx.at.record.pub.cancelapplication.RecoverWorkRecordBeforeAppReflectPub;
import nts.uk.ctx.at.request.dom.application.ReasonNotReflect;
import nts.uk.ctx.at.request.dom.application.ReasonNotReflectDaily;
import nts.uk.ctx.at.request.dom.application.ReflectedState;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.cancelapplication.RQRecoverAppReflectOutputImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.cancelapplication.RecoverRCBeforeAppReflectAdaper;
import nts.uk.ctx.at.request.dom.applicationreflect.object.ReflectStatusResult;
import nts.uk.ctx.at.shared.dom.scherec.application.common.ApplicationShare;
import nts.uk.shr.com.enumcommon.NotUseAtr;

@Stateless
public class RecoverRCBeforeAppReflectAdaperImpl implements RecoverRCBeforeAppReflectAdaper {

	@Inject
	private RecoverWorkRecordBeforeAppReflectPub pub;

	@Override
	public RQRecoverAppReflectOutputImport process(ApplicationShare application, GeneralDate date,
			ReflectStatusResult reflectStatus, NotUseAtr dbRegisterClassfi) {
		val result = pub.process(application, date, convertToExport(reflectStatus), dbRegisterClassfi);
		return new RQRecoverAppReflectOutputImport(convertToShare(result.getReflectStatus()), result.getWorkRecord(),
				result.getAtomTask());
	}

	private ReflectStatusResult convertToShare(RCReflectStatusResultExport reflectStatus) {

		return new ReflectStatusResult(EnumAdaptor.valueOf(reflectStatus.getReflectStatus().value, ReflectedState.class),
				EnumAdaptor.valueOf(reflectStatus.getReasonNotReflectWorkRecord().value, ReasonNotReflectDaily.class),
						EnumAdaptor.valueOf(reflectStatus.getReasonNotReflectWorkSchedule().value, ReasonNotReflect.class));
	}

	private RCReflectStatusResultExport convertToExport(ReflectStatusResult reflectStatus) {

		return new RCReflectStatusResultExport(EnumAdaptor.valueOf(reflectStatus.getReflectStatus().value, RCReflectedStateExport.class),
				EnumAdaptor.valueOf(reflectStatus.getReasonNotReflectWorkRecord().value, RCReasonNotReflectDailyExport.class),
						EnumAdaptor.valueOf(reflectStatus.getReasonNotReflectWorkSchedule().value, RCReasonNotReflectExport.class));
	}
}
