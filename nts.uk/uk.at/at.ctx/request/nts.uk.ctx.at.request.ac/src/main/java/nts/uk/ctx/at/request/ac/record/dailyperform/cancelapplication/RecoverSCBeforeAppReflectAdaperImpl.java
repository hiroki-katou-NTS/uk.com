package nts.uk.ctx.at.request.ac.record.dailyperform.cancelapplication;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.ReasonNotReflect;
import nts.uk.ctx.at.request.dom.application.ReasonNotReflectDaily;
import nts.uk.ctx.at.request.dom.application.ReflectedState;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.cancelapplication.RQRecoverAppReflectImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.cancelapplication.RecoverSCBeforeAppReflectAdapter;
import nts.uk.ctx.at.request.dom.applicationreflect.object.ReflectStatusResult;
import nts.uk.ctx.at.schedule.pub.appreflectprocess.appremove.RecoverSCBeforeAppReflectPub;
import nts.uk.ctx.at.schedule.pub.appreflectprocess.appremove.SCRecoverAppReflectExport;
import nts.uk.ctx.at.schedule.pub.appreflectprocess.appremove.export.ReasonNotReflectDailyExport;
import nts.uk.ctx.at.schedule.pub.appreflectprocess.appremove.export.ReasonNotReflectExport;
import nts.uk.ctx.at.schedule.pub.appreflectprocess.appremove.export.ReflectStatusResultExport;
import nts.uk.ctx.at.schedule.pub.appreflectprocess.appremove.export.ReflectedStateExport;
import nts.uk.ctx.at.shared.dom.scherec.application.common.ApplicationShare;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.shr.com.enumcommon.NotUseAtr;

@Stateless
public class RecoverSCBeforeAppReflectAdaperImpl implements RecoverSCBeforeAppReflectAdapter {

	@Inject
	private RecoverSCBeforeAppReflectPub pub;

	@Override
	public RQRecoverAppReflectImport process(ApplicationShare application, GeneralDate date,
			ReflectStatusResult reflectStatus, NotUseAtr dbRegisterClassfi) {
		SCRecoverAppReflectExport export = pub.process(application, date, convertToExport(reflectStatus),
				dbRegisterClassfi);
		return new RQRecoverAppReflectImport(convertToShare(export.getReflectStatus()),
				(IntegrationOfDaily) export.getDomainSchedule(), export.getAtomTask());
	}

	private ReflectStatusResultExport convertToExport(ReflectStatusResult reflectStatus) {

		return new ReflectStatusResultExport(ReflectedStateExport.valueOf(reflectStatus.getReflectStatus().value),
				ReasonNotReflectDailyExport.valueOf(reflectStatus.getReasonNotReflectWorkRecord().value),
				ReasonNotReflectExport.valueOf(reflectStatus.getReasonNotReflectWorkSchedule().value));
	}

	private ReflectStatusResult convertToShare(ReflectStatusResultExport reflectStatus) {

		return new ReflectStatusResult(
				EnumAdaptor.valueOf(reflectStatus.getReflectStatus().value, ReflectedState.class),
				EnumAdaptor.valueOf(reflectStatus.getReasonNotReflectWorkRecord().value, ReasonNotReflectDaily.class),
				EnumAdaptor.valueOf(reflectStatus.getReasonNotReflectWorkSchedule().value, ReasonNotReflect.class));
	}

}
