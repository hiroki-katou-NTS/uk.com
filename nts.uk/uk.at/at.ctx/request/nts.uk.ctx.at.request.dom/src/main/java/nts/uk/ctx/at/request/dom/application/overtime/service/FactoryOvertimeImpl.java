package nts.uk.ctx.at.request.dom.application.overtime.service;

import java.util.List;

import javax.ejb.Stateless;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.request.dom.application.AppReason;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.ReflectPerScheReason;
import nts.uk.ctx.at.request.dom.application.ReflectPlanPerEnforce;
import nts.uk.ctx.at.request.dom.application.ReflectPlanPerState;
import nts.uk.ctx.at.request.dom.application.ReflectPlanScheReason;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.AppApprovalPhase;
import nts.uk.ctx.at.request.dom.application.overtime.AppOverTime;
import nts.uk.ctx.at.request.dom.application.overtime.OverTimeInput;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class FactoryOvertimeImpl implements IFactoryOvertime {

	@Override
	public Application buildApplication(String appID, GeneralDate applicationDate, int prePostAtr, String appReasonID,
			String applicationReason, List<AppApprovalPhase> listAppApprovalPhase) {
		// 会社ID
		String companyId = AppContexts.user().companyId();
		// 入力者
		String persionId = AppContexts.user().personId();
		// 申請者
		String applicantSID = AppContexts.user().employeeId();
		listAppApprovalPhase.forEach(appApprovalPhase -> {
			appApprovalPhase.setAppID(appID);
			String phaseID = appApprovalPhase.getPhaseID();
			appApprovalPhase.setPhaseID(phaseID);
			appApprovalPhase.getListFrame().forEach(approvalFrame -> {
				String frameID = approvalFrame.getFrameID();
				approvalFrame.setFrameID(frameID);
				approvalFrame.getListApproveAccepted().forEach(appAccepted -> {
					String appAcceptedID = appAccepted.getAppAcceptedID();
					appAccepted.setAppAcceptedID(appAcceptedID);
				});
			});
		});
		return new Application(companyId, appID, EnumAdaptor.valueOf(prePostAtr, PrePostAtr.class), GeneralDateTime.now(),
				persionId, new AppReason(applicationReason), applicationDate, new AppReason(applicationReason),
				ApplicationType.OVER_TIME_APPLICATION, applicantSID,
				EnumAdaptor.valueOf(0, ReflectPlanScheReason.class), null,
				EnumAdaptor.valueOf(0, ReflectPlanPerState.class), EnumAdaptor.valueOf(0, ReflectPlanPerEnforce.class),
				EnumAdaptor.valueOf(0, ReflectPerScheReason.class), null,
				EnumAdaptor.valueOf(0, ReflectPlanPerState.class), EnumAdaptor.valueOf(0, ReflectPlanPerEnforce.class),
				applicationDate, applicationDate, listAppApprovalPhase);
	}

	@Override
	public AppOverTime buildAppOverTime(String companyID, String appID, int overTimeAtr, String workTypeCode,
			String siftCode, int workClockFrom1, int workClockTo1, int workClockFrom2, int workClockTo2,
			String divergenceReason, int flexExessTime, int overTimeShiftNight, List<OverTimeInput> overtimeInputs) {
		AppOverTime appOverTime = AppOverTime.createSimpleFromJavaType(companyID, appID, overTimeAtr, workTypeCode,
				siftCode, workClockFrom1, workClockTo1, workClockFrom2, workClockTo2, divergenceReason, flexExessTime,
				overTimeShiftNight);
		appOverTime.setOverTimeInput(overtimeInputs);
		return appOverTime;
	}

}
