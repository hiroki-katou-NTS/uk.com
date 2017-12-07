package nts.uk.ctx.at.request.app.command.application.workchange;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.request.app.command.application.common.CreateApplicationCommand;
import nts.uk.ctx.at.request.app.command.application.common.appapprovalphase.AppApprovalPhaseCmd;
import nts.uk.ctx.at.request.dom.application.AppReason;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.ReflectPerScheReason;
import nts.uk.ctx.at.request.dom.application.ReflectPlanPerEnforce;
import nts.uk.ctx.at.request.dom.application.ReflectPlanPerState;
import nts.uk.ctx.at.request.dom.application.ReflectPlanScheReason;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.AppApprovalPhase;
import nts.uk.ctx.at.request.dom.application.workchange.AppWorkChange;
import nts.uk.ctx.at.request.dom.application.workchange.IWorkChangeUpdateService;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class UpdateAppWorkChangeCommandHandler extends CommandHandler<AddAppWorkChangeCommand> {

	@Inject
	private IWorkChangeUpdateService updateService;

	@Override
	protected void handle(CommandHandlerContext<AddAppWorkChangeCommand> context) {
		AddAppWorkChangeCommand updateCommand = context.getCommand();
		// Command data
		CreateApplicationCommand appCommand = updateCommand.getApplication();
		AppWorkChangeCommand workChangeCommand = updateCommand.getWorkChange();
		List<AppApprovalPhaseCmd> approvalPhaseCommand = updateCommand.getAppApprovalPhases();
		
		// 会社ID
		String companyId = AppContexts.user().companyId();
		// 申請ID
		String appID = appCommand.getApplicationID();
		// Phase list
		List<AppApprovalPhase> pharseList = ApprovalPhaseUtils.convertToDomain(approvalPhaseCommand, companyId, appID);
		// 申請
		Application updateApp = new Application(companyId,
				// command.goBackCommand.getAppID(),
				appID, EnumAdaptor.valueOf(appCommand.getPrePostAtr(), PrePostAtr.class), appCommand.getInputDate(),
				appCommand.getEnteredPersonSID(), new AppReason(appCommand.getReversionReason()),
				appCommand.getApplicationDate(), new AppReason(appCommand.getApplicationReason()),
				EnumAdaptor.valueOf(appCommand.getApplicationType(), ApplicationType.class),
				appCommand.getApplicantSID(),
				EnumAdaptor.valueOf(appCommand.getReflectPlanScheReason(), ReflectPlanScheReason.class),
				appCommand.getReflectPlanTime(),
				EnumAdaptor.valueOf(appCommand.getReflectPlanState(), ReflectPlanPerState.class),
				EnumAdaptor.valueOf(appCommand.getReflectPlanEnforce(), ReflectPlanPerEnforce.class),
				EnumAdaptor.valueOf(appCommand.getReflectPerScheReason(), ReflectPerScheReason.class),
				appCommand.getReflectPerTime(),
				EnumAdaptor.valueOf(appCommand.getReflectPerState(), ReflectPlanPerState.class),
				EnumAdaptor.valueOf(appCommand.getReflectPerEnforce(), ReflectPlanPerEnforce.class),
				appCommand.getStartDate(), appCommand.getEndDate(), pharseList);

		// 勤務変更申請
		AppWorkChange workChangeDomain = AppWorkChange.createFromJavaType(workChangeCommand.getCid(),
				workChangeCommand.getAppId(), workChangeCommand.getWorkTypeCd(), workChangeCommand.getWorkTimeCd(),
				workChangeCommand.getExcludeHolidayAtr(), workChangeCommand.getWorkChangeAtr(),
				workChangeCommand.getGoWorkAtr1(), workChangeCommand.getBackHomeAtr1(),
				workChangeCommand.getBreakTimeStart1(), workChangeCommand.getBreakTimeEnd1(),
				workChangeCommand.getWorkTimeStart1(), workChangeCommand.getWorkTimeEnd1(),
				workChangeCommand.getWorkTimeStart2(), workChangeCommand.getWorkTimeEnd2(),
				workChangeCommand.getGoWorkAtr2(), workChangeCommand.getBackHomeAtr2());
		//OptimisticLock
		workChangeDomain.setVersion(workChangeCommand.getVersion());
		updateApp.setVersion(workChangeCommand.getVersion());
		
		// アルゴリズム「勤務変更申請登録（更新）」を実行する
		updateService.UpdateWorkChange(updateApp, workChangeDomain);
	}

}
