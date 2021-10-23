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
import nts.uk.ctx.at.schedule.pub.appreflectprocess.export.SCReasonNotReflectDailyExport;
import nts.uk.ctx.at.schedule.pub.appreflectprocess.export.SCReasonNotReflectExport;
import nts.uk.ctx.at.schedule.pub.appreflectprocess.export.SCReflectStatusResultExport;
import nts.uk.ctx.at.schedule.pub.appreflectprocess.export.SCReflectedStateExport;
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
				export.getDomainSchedule().map(x -> (IntegrationOfDaily) x), export.getAtomTask());
	}

	private SCReflectStatusResultExport convertToExport(ReflectStatusResult reflectStatus) {

		return new SCReflectStatusResultExport(
				EnumAdaptor.valueOf(reflectStatus.getReflectStatus().value, SCReflectedStateExport.class),
				reflectStatus.getReasonNotReflectWorkRecord() == null ? null : EnumAdaptor.valueOf(reflectStatus.getReasonNotReflectWorkRecord().value,
						SCReasonNotReflectDailyExport.class),
						reflectStatus.getReasonNotReflectWorkSchedule() == null ? null : EnumAdaptor.valueOf(reflectStatus.getReasonNotReflectWorkSchedule().value,
						SCReasonNotReflectExport.class));
	}

	private ReflectStatusResult convertToShare(SCReflectStatusResultExport reflectStatus) {

		return new ReflectStatusResult(
				EnumAdaptor.valueOf(reflectStatus.getReflectStatus().value, ReflectedState.class),
				reflectStatus.getReasonNotReflectWorkRecord() == null ? null : EnumAdaptor.valueOf(reflectStatus.getReasonNotReflectWorkRecord().value, ReasonNotReflectDaily.class),
				reflectStatus.getReasonNotReflectWorkSchedule() == null ? null : EnumAdaptor.valueOf(reflectStatus.getReasonNotReflectWorkSchedule().value, ReasonNotReflect.class));
	}

}
