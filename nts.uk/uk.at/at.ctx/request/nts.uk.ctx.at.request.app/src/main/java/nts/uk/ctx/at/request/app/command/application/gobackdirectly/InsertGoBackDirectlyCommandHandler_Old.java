package nts.uk.ctx.at.request.app.command.application.gobackdirectly;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.request.dom.application.IFactoryApplication;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.service.GoBackDirectlyRegisterService;
import nts.uk.ctx.at.request.dom.setting.request.application.applicationsetting.ApplicationSettingRepository;

@Stateless
@Transactional
public class InsertGoBackDirectlyCommandHandler_Old extends CommandHandlerWithResult<InsertApplicationGoBackDirectlyCommand, ProcessResult> {
	@Inject
	private GoBackDirectlyRegisterService goBackDirectlyRegisterService;
	
	@Inject
	ApplicationSettingRepository applicationSettingRepository;
	
//	@Inject
//	private AppTypeDiscreteSettingRepository appTypeDiscreteSettingRepository;
	@Inject
	private IFactoryApplication iFactoryApplication;
	
	@Override
	protected ProcessResult handle(CommandHandlerContext<InsertApplicationGoBackDirectlyCommand> context) {
		// refactor 4 error 
		/*String companyId = AppContexts.user().companyId();
		InsertApplicationGoBackDirectlyCommand command = context.getCommand();
		
		//get new Application Item
		AppTypeDiscreteSetting appTypeDiscreteSetting = appTypeDiscreteSettingRepository.getAppTypeDiscreteSettingByAppType(
				companyId, 
				ApplicationType_Old.GO_RETURN_DIRECTLY_APPLICATION.value).get();
		String appReason = Strings.EMPTY;	
		String typicalReason = Strings.EMPTY;
		String displayReason = Strings.EMPTY;
		if(appTypeDiscreteSetting.getTypicalReasonDisplayFlg().equals(AppDisplayAtr.DISPLAY)){
			typicalReason += command.appCommand.getAppReasonID();
		}
		if(appTypeDiscreteSetting.getDisplayReasonFlg().equals(AppDisplayAtr.DISPLAY)){
			if(Strings.isNotBlank(typicalReason)){
				displayReason += System.lineSeparator();
			}
			displayReason += command.appCommand.getApplicationReason();
		}
		Optional<ApplicationSetting> applicationSettingOp = applicationSettingRepository
				.getApplicationSettingByComID(companyId);
		ApplicationSetting applicationSetting = applicationSettingOp.get();
		if(appTypeDiscreteSetting.getTypicalReasonDisplayFlg().equals(AppDisplayAtr.DISPLAY)
			||appTypeDiscreteSetting.getDisplayReasonFlg().equals(AppDisplayAtr.DISPLAY)){
			if (applicationSetting.getRequireAppReasonFlg().equals(RequiredFlg.REQUIRED)
					&& Strings.isBlank(typicalReason+displayReason)) {
				throw new BusinessException("Msg_115");
			}
		}
		appReason = typicalReason + displayReason;
		// 申請ID
		String appID = IdentifierUtil.randomUniqueId();
//		Application_New newApp = Application_New.firstCreate(
//				companyId, 
//				EnumAdaptor.valueOf(command.appCommand.getPrePostAtr(), PrePostAtr.class),  
//				command.appCommand.getApplicationDate(),
//				EnumAdaptor.valueOf(command.appCommand.getApplicationType(), ApplicationType.class), 
//				command.appCommand.getEnteredPersonSID(),
//				new AppReason(appReason));
		Application_New appRoot = iFactoryApplication.buildApplication(appID, command.appCommand.getApplicationDate(),
				command.appCommand.getPrePostAtr(), appReason,appReason,
				ApplicationType_Old.GO_RETURN_DIRECTLY_APPLICATION, command.appCommand.getApplicationDate(), command.appCommand.getApplicationDate(), command.appCommand.getApplicantSID());
		// get new GoBack Direct Item
		GoBackDirectly_Old newGoBack = new GoBackDirectly_Old(
				companyId, 
				appID,
				command.goBackCommand.workTypeCD, 
				command.goBackCommand.siftCD, 
				command.goBackCommand.workChangeAtr,
				command.goBackCommand.goWorkAtr1, 
				command.goBackCommand.backHomeAtr1,
				command.goBackCommand.workTimeStart1, 
				command.goBackCommand.workTimeEnd1,
				command.goBackCommand.workLocationCD1, 
				command.goBackCommand.goWorkAtr2,
				command.goBackCommand.backHomeAtr2, 
				command.goBackCommand.workTimeStart2,
				command.goBackCommand.workTimeEnd2, 
				command.goBackCommand.workLocationCD2);
		//勤務を変更する
		
		//直行直帰登録
		return goBackDirectlyRegisterService.register(newGoBack, appRoot);*/
		return null;
	}
}
