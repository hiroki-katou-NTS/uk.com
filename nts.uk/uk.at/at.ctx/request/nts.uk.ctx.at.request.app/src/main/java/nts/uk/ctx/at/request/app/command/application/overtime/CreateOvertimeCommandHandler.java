package nts.uk.ctx.at.request.app.command.application.overtime;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.apache.logging.log4j.util.Strings;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.request.app.find.application.overtime.dto.OvertimeSettingData;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.UseAtr;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.RegisterAtApproveReflectionInfoService;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.after.NewAfterRegister;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;
import nts.uk.ctx.at.request.dom.application.overtime.AppOverTime_Old;
import nts.uk.ctx.at.request.dom.application.overtime.AppOvertimeDetail;
import nts.uk.ctx.at.request.dom.application.overtime.service.IFactoryOvertime;
import nts.uk.ctx.at.request.dom.application.overtime.service.OvertimeService;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon.OvertimeRestAppCommonSetting;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class CreateOvertimeCommandHandler extends CommandHandlerWithResult<CreateOvertimeCommand, ProcessResult> {

	@Inject
	private IFactoryOvertime factoryOvertime;

	@Inject
	private OvertimeService overTimeService;

	@Inject
	private NewAfterRegister newAfterRegister;

	@Inject
	private RegisterAtApproveReflectionInfoService registerService;

	@Override
	protected ProcessResult handle(CommandHandlerContext<CreateOvertimeCommand> context) {

		CreateOvertimeCommand command = context.getCommand();
		// 会社ID
		String companyId = AppContexts.user().companyId();
		// 申請ID
		String appID = IdentifierUtil.randomUniqueId();
		
		OvertimeSettingData overtimeSettingData = command.getOvertimeSettingDataDto().toDomain();
		
//		AppTypeDiscreteSetting appTypeDiscreteSetting = overtimeSettingData.appCommonSettingOutput.appTypeDiscreteSettings
//				.stream().filter(x -> x.getAppType()==ApplicationType.OVER_TIME_APPLICATION).findAny().get();
//		String appReason = Strings.EMPTY;	
//		String typicalReason = Strings.EMPTY;
//		String displayReason = Strings.EMPTY;
//		if(appTypeDiscreteSetting.getTypicalReasonDisplayFlg() == DisplayAtr.DISPLAY){
//			typicalReason += command.getAppReasonID();
//		}
//		if(appTypeDiscreteSetting.getDisplayReasonFlg() == AppDisplayAtr.DISPLAY){
//			if(Strings.isNotBlank(typicalReason)){
//				displayReason += System.lineSeparator();
//			}
//			displayReason += command.getApplicationReason();
//		}
//		ApplicationSetting applicationSetting = overtimeSettingData.appCommonSettingOutput.applicationSetting;
//		if(appTypeDiscreteSetting.getTypicalReasonDisplayFlg() == DisplayAtr.DISPLAY
//			||appTypeDiscreteSetting.getDisplayReasonFlg() == AppDisplayAtr.DISPLAY){
//			if (applicationSetting.getRequireAppReasonFlg().equals(RequiredFlg.REQUIRED)
//					&& Strings.isBlank(typicalReason+displayReason)) {
//				throw new BusinessException("Msg_115");
//			}
//		}
//		appReason = typicalReason + displayReason;
		
		String divergenceReason = Strings.EMPTY;  
		String divergenceReasonCombox = Strings.EMPTY;
		String divergenceReasonArea = Strings.EMPTY;
		
		Integer prePostAtr = command.getPrePostAtr();
		
		OvertimeRestAppCommonSetting overtimeRestAppCommonSet = overtimeSettingData.overtimeRestAppCommonSet;
		boolean displayDivergenceReasonCombox = (prePostAtr != PrePostAtr.PREDICT.value) && (overtimeRestAppCommonSet.getDivergenceReasonFormAtr().value == UseAtr.USE.value);
		boolean displayDivergenceReasonArea = (prePostAtr != PrePostAtr.PREDICT.value) && (overtimeRestAppCommonSet.getDivergenceReasonInputAtr().value == UseAtr.USE.value);
		if(displayDivergenceReasonCombox){
			divergenceReasonCombox += command.getDivergenceReasonContent();
		}
		if(displayDivergenceReasonArea){
			if(Strings.isNotBlank(divergenceReasonCombox)){
				divergenceReasonArea += System.lineSeparator();
			}
			divergenceReasonArea += command.getDivergenceReasonArea();
		}
		
		divergenceReason = divergenceReasonCombox + divergenceReasonArea;
		// Create Application
//		Application_New appRoot = factoryOvertime.buildApplication(appID, command.getApplicationDate(),
//				prePostAtr, appReason,
//				appReason,command.getApplicantSID());
		Application appRoot = null;

		Integer workClockFrom1 = command.getWorkClockFrom1() == null ? null : command.getWorkClockFrom1().intValue();
		Integer workClockTo1 = command.getWorkClockTo1() == null ? null : command.getWorkClockTo1().intValue();

		Optional<AppOvertimeDetail> appOvertimeDetailOtp = command.getAppOvertimeDetail() == null ? Optional.empty()
				: Optional.ofNullable(command.getAppOvertimeDetail().toDomain(companyId, appID));
		AppOverTime_Old overTimeDomain = factoryOvertime.buildAppOverTime(companyId, appID, command.getOvertimeAtr(),
				command.getWorkTypeCode(), command.getSiftTypeCode(), workClockFrom1, workClockTo1, null, null, 
				divergenceReason, command.getFlexExessTime(), command.getOverTimeShiftNight(),
				CheckBeforeRegisterOvertime.getOverTimeInput(command, companyId, appID), 
				appOvertimeDetailOtp);

		// ドメインモデル「残業申請」の登録処理を実行する(INSERT)
		overTimeService.CreateOvertime(overTimeDomain, appRoot);

		// 2-2.新規画面登録時承認反映情報の整理
		// error EA refactor 4
		/*registerService.newScreenRegisterAtApproveInfoReflect(appRoot.getEmployeeID(), appRoot);*/

		// 2-3.新規画面登録後の処理を実行
		/*return newAfterRegister.processAfterRegister(appRoot);*/
		return null;
	}
}
