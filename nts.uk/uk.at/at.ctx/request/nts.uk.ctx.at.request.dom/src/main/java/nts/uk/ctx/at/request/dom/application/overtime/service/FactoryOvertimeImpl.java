package nts.uk.ctx.at.request.dom.application.overtime.service;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.AppReason;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.ReflectPlanPerEnforce;
import nts.uk.ctx.at.request.dom.application.ReflectPlanPerState;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.AppApprovalPhase;
import nts.uk.ctx.at.request.dom.application.overtime.AppOverTime;
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
		return new Application(companyId, appID, EnumAdaptor.valueOf(prePostAtr, PrePostAtr.class), GeneralDate.today(),
				persionId, new AppReason(applicationReason), applicationDate, appReasonID,
				new AppReason(applicationReason), ApplicationType.OVER_TIME_APPLICATION, applicantSID, null, null,
				EnumAdaptor.valueOf(0, ReflectPlanPerState.class), EnumAdaptor.valueOf(0, ReflectPlanPerEnforce.class),
				null, null, EnumAdaptor.valueOf(0, ReflectPlanPerState.class),
				EnumAdaptor.valueOf(0, ReflectPlanPerEnforce.class), null, null, listAppApprovalPhase);
	}

	@Override
	public AppOverTime buildAppOverTime() {
		// TODO Auto-generated method stub
		return null;
	}

}
