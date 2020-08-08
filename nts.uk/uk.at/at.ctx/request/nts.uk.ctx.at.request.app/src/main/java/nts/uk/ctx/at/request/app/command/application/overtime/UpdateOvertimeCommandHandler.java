package nts.uk.ctx.at.request.app.command.application.overtime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.logging.log4j.util.Strings;

import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.gul.text.StringUtil;
import nts.uk.ctx.at.request.app.find.application.overtime.dto.OvertimeSettingData;
import nts.uk.ctx.at.request.dom.application.AppReason;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository_New;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.UseAtr;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.InitMode;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after.DetailAfterUpdate;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before.DetailBeforeUpdate;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output.OutputMode;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output.User;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;
import nts.uk.ctx.at.request.dom.application.overtime.AppOverTime;
import nts.uk.ctx.at.request.dom.application.overtime.AppOvertimeDetail;
import nts.uk.ctx.at.request.dom.application.overtime.OverTimeAtr;
import nts.uk.ctx.at.request.dom.application.overtime.OverTimeInput;
import nts.uk.ctx.at.request.dom.application.overtime.OvertimeRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon.OvertimeRestAppCommonSetting;
import nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.displaysetting.DisplayAtr;
import nts.uk.ctx.at.request.dom.setting.request.application.applicationsetting.ApplicationSetting;
import nts.uk.ctx.at.request.dom.setting.request.application.apptypediscretesetting.AppTypeDiscreteSetting;
import nts.uk.ctx.at.request.dom.setting.request.application.common.RequiredFlg;
import nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.primitive.AppDisplayAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.InterimRemainDataMngRegisterDateChange;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class UpdateOvertimeCommandHandler extends CommandHandlerWithResult<UpdateOvertimeCommand, ProcessResult>{

	@Inject
	private OvertimeRepository overtimeRepository;
	
	@Inject
	private DetailBeforeUpdate detailBeforeUpdate;
	
	@Inject
	private DetailAfterUpdate detailAfterUpdate;
	
	@Inject
	private ApplicationRepository_New applicationRepository;
	
	@Inject
	private InterimRemainDataMngRegisterDateChange interimRemainDataMngRegisterDateChange;
	
	@Inject
	private InitMode initMode;

	@Override
	protected ProcessResult handle(CommandHandlerContext<UpdateOvertimeCommand> context) {
		String companyID = AppContexts.user().companyId();
		UpdateOvertimeCommand command = context.getCommand();
		OvertimeSettingData overtimeSettingData = command.getOvertimeSettingDataDto().toDomain();
		Optional<AppOverTime> opAppOverTime = overtimeRepository.getFullAppOvertime(companyID, command.getAppID());
		if(!opAppOverTime.isPresent()){
			throw new RuntimeException("khong ton tai doi tuong de update");
		}
		OutputMode outputMode = initMode.getDetailScreenInitMode(EnumAdaptor.valueOf(context.getCommand().getUser(), User.class), context.getCommand().getReflectPerState());
		String appReason = opAppOverTime.get().getApplication().getAppReason().v();
		String divergenceReason = opAppOverTime.get().getDivergenceReason(); 
		if(outputMode==OutputMode.EDITMODE){
			AppTypeDiscreteSetting appTypeDiscreteSetting = overtimeSettingData.appCommonSettingOutput.appTypeDiscreteSettings
					.stream().filter(x -> x.getAppType()==ApplicationType.OVER_TIME_APPLICATION).findAny().get();
			String typicalReason = Strings.EMPTY;
			String displayReason = Strings.EMPTY;
			if(appTypeDiscreteSetting.getTypicalReasonDisplayFlg().equals(DisplayAtr.DISPLAY)){
				typicalReason += command.getAppReasonID();
			}
			if(appTypeDiscreteSetting.getDisplayReasonFlg().equals(AppDisplayAtr.DISPLAY)){
				if(Strings.isNotBlank(typicalReason)){
					displayReason += System.lineSeparator();
				}
				displayReason += command.getApplicationReason();
			} else {
				if(Strings.isBlank(typicalReason)){
					displayReason = applicationRepository.findByID(companyID, command.getAppID()).get().getAppReason().v();
				}
			}
			ApplicationSetting applicationSetting = overtimeSettingData.appCommonSettingOutput.applicationSetting;
			if(appTypeDiscreteSetting.getTypicalReasonDisplayFlg().equals(DisplayAtr.DISPLAY)
				||appTypeDiscreteSetting.getDisplayReasonFlg().equals(AppDisplayAtr.DISPLAY)){
				if (applicationSetting.getRequireAppReasonFlg().equals(RequiredFlg.REQUIRED)
						&& Strings.isBlank(typicalReason+displayReason)) {
					throw new BusinessException("Msg_115");
				}
				appReason = typicalReason + displayReason;
			}
			
			String divergenceReasonCombox = Strings.EMPTY;
			String divergenceReasonArea = Strings.EMPTY;
			
			Integer prePostAtr = opAppOverTime.get().getApplication().getPrePostAtr().value;
			
			OvertimeRestAppCommonSetting overtimeRestAppCommonSet = overtimeSettingData.overtimeRestAppCommonSet;
			boolean displayDivergenceReasonCombox = (prePostAtr != PrePostAtr.PREDICT.value) && (overtimeRestAppCommonSet.getDivergenceReasonFormAtr().value == UseAtr.USE.value);
			boolean displayDivergenceReasonArea = (prePostAtr != PrePostAtr.PREDICT.value) && (overtimeRestAppCommonSet.getDivergenceReasonInputAtr().value == UseAtr.USE.value);
			if(displayDivergenceReasonCombox){
				divergenceReasonCombox += command.getDivergenceReasonContent();
			}
			if(displayDivergenceReasonArea){
				if(Strings.isNotBlank(typicalReason)){
					divergenceReasonArea += System.lineSeparator();
				}
				divergenceReasonArea += command.getDivergenceReasonArea();
			}
			
			if(displayDivergenceReasonCombox||displayDivergenceReasonArea){
				divergenceReason = divergenceReasonCombox + divergenceReasonArea;
			}
		}
		
		AppOverTime appOverTime = opAppOverTime.get();
		List<OverTimeInput> overTimeInputs = new ArrayList<>();
		overTimeInputs.addAll(command.getRestTime().stream().filter(x -> x.getStartTime()!=null||x.getEndTime()!=null).map(x -> x.convertToDomain()).collect(Collectors.toList()));
		overTimeInputs.addAll(command.getOvertimeHours().stream().filter(x -> x.getApplicationTime()!=null).map(x -> x.convertToDomain()).collect(Collectors.toList()));
		Optional<AppOvertimeDetail> appOvertimeDetailOtp = command.getAppOvertimeDetail() == null ? Optional.empty()
				: Optional.ofNullable(command.getAppOvertimeDetail().toDomain(companyID, appOverTime.getAppID()));
		String applicationReason = appReason;
		appOverTime.setDivergenceReason(divergenceReason);
		appOverTime.setFlexExessTime(command.getFlexExessTime());
		appOverTime.setOverTimeAtr(EnumAdaptor.valueOf(command.getOvertimeAtr(), OverTimeAtr.class));
		appOverTime.setOverTimeInput(overTimeInputs);
		appOverTime.setAppOvertimeDetail(appOvertimeDetailOtp);
		appOverTime.setOverTimeShiftNight(command.getOverTimeShiftNight());
		appOverTime.setSiftCode(StringUtil.isNullOrEmpty(command.getSiftTypeCode(), true)? null : new WorkTimeCode(command.getSiftTypeCode()));
		appOverTime.setWorkClockFrom1(command.getWorkClockFrom1());
		appOverTime.setWorkClockTo1(command.getWorkClockTo1());
		appOverTime.setWorkTypeCode( StringUtil.isNullOrEmpty(command.getWorkTypeCode(), true)? null : new WorkTypeCode(command.getWorkTypeCode()));
		appOverTime.getApplication().setAppReason(new AppReason(applicationReason));
		appOverTime.setVersion(appOverTime.getVersion());
		appOverTime.getApplication().setVersion(command.getVersion());
		//4-1.詳細画面登録前の処理を実行する
		// error EA refactor 4
		/*detailBeforeUpdate.processBeforeDetailScreenRegistration(
				companyID, 
				appOverTime.getApplication().getEmployeeID(), 
				appOverTime.getApplication().getAppDate(), 
				1, 
				appOverTime.getAppID(), 
				appOverTime.getApplication().getPrePostAtr(), command.getVersion(),
				appOverTime.getWorkTypeCode() == null ? null : appOverTime.getWorkTypeCode().v(),
				appOverTime.getSiftCode() == null ? null : appOverTime.getSiftCode().v());*/
		//ドメインモデル「残業申請」を更新する
		overtimeRepository.update(appOverTime);
		applicationRepository.updateWithVersion(appOverTime.getApplication());
		// 暫定データの登録
		interimRemainDataMngRegisterDateChange.registerDateChange(
				companyID, 
				command.getApplicantSID(), 
				Arrays.asList(command.getApplicationDate()));
		//4-2.詳細画面登録後の処理を実行する
		// error EA refactor 4
		/*return detailAfterUpdate.processAfterDetailScreenRegistration(appOverTime.getApplication());*/
		return null;
	}

}
