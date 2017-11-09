package nts.uk.ctx.at.request.dom.application.overtime.service;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.UseAtr;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.EmployeeRequestAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.SWkpHistImport;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.output.AppCommonSettingOutput;
import nts.uk.ctx.at.request.dom.application.common.service.other.OtherCommonAlgorithm;
import nts.uk.ctx.at.request.dom.application.overtime.AppOverTime;
import nts.uk.ctx.at.request.dom.application.overtime.OverTimeAtr;
import nts.uk.ctx.at.request.dom.application.overtime.OverTimeInput;
import nts.uk.ctx.at.request.dom.application.overtime.OvertimeInputRepository;
import nts.uk.ctx.at.request.dom.application.overtime.OvertimeRepository;
import nts.uk.ctx.at.request.dom.overtimeinstruct.OverTimeInstruct;
import nts.uk.ctx.at.request.dom.overtimeinstruct.OvertimeInstructRepository;
import nts.uk.ctx.at.request.dom.setting.applicationreason.ApplicationReason;
import nts.uk.ctx.at.request.dom.setting.applicationreason.ApplicationReasonRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon.OvertimeRestAppCommonSetting;
import nts.uk.ctx.at.request.dom.setting.company.divergencereason.DivergenceReason;
import nts.uk.ctx.at.request.dom.setting.company.divergencereason.DivergenceReasonRepository;
import nts.uk.ctx.at.request.dom.setting.request.application.applicationsetting.ApplicationSetting;
import nts.uk.ctx.at.request.dom.setting.request.application.applicationsetting.ApplicationSettingRepository;
import nts.uk.ctx.at.request.dom.setting.request.application.apptypediscretesetting.AppTypeDiscreteSetting;
import nts.uk.ctx.at.request.dom.setting.request.application.apptypediscretesetting.AppTypeDiscreteSettingRepository;
import nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.primitive.AppDisplayAtr;
import nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.primitive.InitValueAtr;
import nts.uk.ctx.at.request.dom.setting.requestofeach.AtWorkAtr;
import nts.uk.ctx.at.request.dom.setting.requestofeach.DisplayFlg;
import nts.uk.ctx.at.request.dom.setting.requestofeach.RequestAppDetailSetting;
import nts.uk.ctx.at.shared.dom.employmentrule.hourlate.overtime.overtimeframe.OvertimeFrameRepository;

@Stateless
public class OvertimePreProcessImpl implements IOvertimePreProcess{
	
	final String DATE_FORMAT = "yyyy/MM/dd";
	@Inject
	private OvertimeInstructRepository overtimeInstructRepository;
	@Inject
	private ApplicationSettingRepository applicationSettingRepository;
	@Inject
	private AppTypeDiscreteSettingRepository discreteRepo;
	@Inject
	private OtherCommonAlgorithm otherCommonAlgorithm;
	@Inject
	private EmployeeRequestAdapter employeeAdapter;
	@Inject
	private ApplicationReasonRepository applicationReasonRepository;
	@Inject
	private DivergenceReasonRepository diReasonRepository;
	@Inject
	private ApplicationRepository applicationRepository;
	
	@Inject
	private OvertimeRepository overtimeRepository;
	
	@Inject
	private OvertimeInputRepository overtimeInputRepository;
	
	
	@Override
	public OverTimeInstruct getOvertimeInstruct(AppCommonSettingOutput appCommonSettingOutput,String appDate,String employeeID) {
		OverTimeInstruct overtimeInstruct = new OverTimeInstruct();
		if(appCommonSettingOutput != null){
			int useAtr = appCommonSettingOutput.requestOfEachCommon.getRequestAppDetailSettings().get(0).getUserAtr().value;
			if(useAtr == UseAtr.USE.value){
				if(appDate != null){
					overtimeInstruct = overtimeInstructRepository.getOvertimeInstruct(GeneralDate.fromString(appDate, DATE_FORMAT), employeeID);
				}
			}
		}
		return overtimeInstruct;
	}

	@Override
	public DisplayPrePost getDisplayPrePost(String companyID, int uiType, String appDate) {
		Optional<ApplicationSetting> applicationSetting = applicationSettingRepository.getApplicationSettingByComID(companyID);
		DisplayPrePost result = new DisplayPrePost();
		if(applicationSetting.isPresent()){
			// if display then check What call UI?
			if(applicationSetting.get().getDisplayPrePostFlg().value == AppDisplayAtr.DISPLAY.value){
				result.setDisplayPrePostFlg(AppDisplayAtr.DISPLAY.value);
				/**
				 * check UI
				 * 0: メニューから起動 :menu
				 * other: 日別修正、トップページアラームから起動,残業指示から起動
				 */
				if(uiType == 0){
					Optional<AppTypeDiscreteSetting> discreteSetting = discreteRepo.getAppTypeDiscreteSettingByAppType(companyID, ApplicationType.OVER_TIME_APPLICATION.value);
					if(discreteSetting.isPresent()){
						result.setPrePostAtr(discreteSetting.get().getPrePostInitFlg().value);
					}
				}else{
					//事後申請として起動する(khoi dong cai xin sau len)
					result.setPrePostAtr(InitValueAtr.POST.value);
					
				}
			}else{
				//if not display
				result.setDisplayPrePostFlg(AppDisplayAtr.NOTDISPLAY.value);
				result.setPrePostAtr(this.otherCommonAlgorithm.preliminaryJudgmentProcessing(EnumAdaptor.valueOf(ApplicationType.OVER_TIME_APPLICATION.value,ApplicationType.class), GeneralDate.fromString(appDate, DATE_FORMAT)).value);
			}
		}
		return result;
	}

	@Override
	public void getWorkingHours(String companyID, String employeeID, String appDate,
			Optional<RequestAppDetailSetting> requestAppDetailSetting) {
		if(requestAppDetailSetting.isPresent()){
			if(appDate != null){
				int atWorkAtr = requestAppDetailSetting.get().getAtworkTimeBeginDisFlg().value;
				if(atWorkAtr == AtWorkAtr.DISPLAY.value){
					// team anh lương
				}
			}
		}
	}

	@Override
	public boolean getRestTime(Optional<RequestAppDetailSetting> requestAppDetailSetting) {
		if(requestAppDetailSetting.isPresent()){
			if(requestAppDetailSetting.get().getBreakTimeDisFlg().value == DisplayFlg.DISPLAY.value){
				return true;
			}else{
				return false;
			}
		}
		return false;
	}

	@Override
	public void getOvertimeHours(int overtimeAtr) {
		//早出残業の場合
		if(overtimeAtr == OverTimeAtr.PREOVERTIME.value){
			
		}
		//通常残業の場合
		if(overtimeAtr == OverTimeAtr.REGULAROVERTIME.value){
			
		}
		//早出残業・通常残業の場合
		if(overtimeAtr == OverTimeAtr.ALL.value){
			
		}
	}

	@Override
	public void getBonusTime(String employeeID, Optional<OvertimeRestAppCommonSetting> overtimeRestAppCommonSet,
			String appDate) {
		if(overtimeRestAppCommonSet.get().getBonusTimeDisplayAtr().value == UseAtr.USE.value){
			// アルゴリズム「社員所属職場履歴を取得」を実行する
			SWkpHistImport sWkpHistImport = employeeAdapter.getSWkpHistByEmployeeID(employeeID, GeneralDate.fromString(appDate, DATE_FORMAT));
			//アルゴリズム「職場の特定日設定を取得する」を実行する (hung lam)
			
		}
	}

	@Override
	public List<ApplicationReason> getApplicationReasonType(String companyID, int appType, Optional<AppTypeDiscreteSetting> appTypeDiscreteSetting) {
		if(appTypeDiscreteSetting.get().getTypicalReasonDisplayFlg().value == AppDisplayAtr.DISPLAY.value){
			List<ApplicationReason> applicationReasons = applicationReasonRepository.getReasonByAppType(companyID,
					appType);
			return applicationReasons;
		}
		return null;
	}

	@Override
	public boolean displayAppReasonContentFlg(Optional<AppTypeDiscreteSetting> appTypeDiscreteSetting) {
		if(appTypeDiscreteSetting.get().getDisplayReasonFlg().value == AppDisplayAtr.DISPLAY.value){
			return true;
		}
		return false;
	}

	@Override
	public List<DivergenceReason> getDivergenceReasonForm(String companyID, int appType,
			Optional<OvertimeRestAppCommonSetting> overtimeRestAppCommonSet) {
		if(overtimeRestAppCommonSet.get().getDivergenceReasonFormAtr().value == UseAtr.USE.value){
			List<DivergenceReason> divergenceReasons = diReasonRepository.getDivergenceReason(companyID, ApplicationType.OVER_TIME_APPLICATION.value);
			return divergenceReasons;
		}
		return null;
	}

	@Override
	public boolean displayDivergenceReasonInput(Optional<OvertimeRestAppCommonSetting> overtimeRestAppCommonSet) {
		if(overtimeRestAppCommonSet.get().getDivergenceReasonInputAtr().value == UseAtr.USE.value){
			return true;
		}
		return false;
	}

	@Override
	public AppOverTime getPreApplication(String employeeId,
			Optional<OvertimeRestAppCommonSetting> overtimeRestAppCommonSet, String appDate, int prePostAtr) {
		AppOverTime result = new AppOverTime();
		if(prePostAtr == InitValueAtr.POST.value){
			Application applicationOvertime = new Application();
			if(overtimeRestAppCommonSet.get().getPreDisplayAtr().value == UseAtr.USE.value){
				Optional<Application> application = this.applicationRepository.getApp(employeeId,  GeneralDate.fromString(appDate, DATE_FORMAT), PrePostAtr.PREDICT.value, ApplicationType.OVER_TIME_APPLICATION.value);
				if(application.isPresent()){
					applicationOvertime.setApplicationDate(application.get().getApplicationDate());
					Optional<AppOverTime> appOvertime = this.overtimeRepository.getAppOvertime(application.get().getCompanyID(), application.get().getApplicationID());
					if(appOvertime.isPresent()){
						result.setWorkTypeCode(appOvertime.get().getWorkTypeCode());
						result.setSiftCode(appOvertime.get().getSiftCode());
						result.setWorkClockFrom1(appOvertime.get().getWorkClockFrom1());
						result.setWorkClockTo1(appOvertime.get().getWorkClockTo1());
						result.setWorkClockFrom2(appOvertime.get().getWorkClockFrom2());
						result.setWorkClockTo2(appOvertime.get().getWorkClockTo2());
						
						List<OverTimeInput> overtimeInputs = overtimeInputRepository.getOvertimeInput(appOvertime.get().getCompanyID(), appOvertime.get().getAppID());
						result.setOverTimeInput(overtimeInputs);
						result.setOverTimeShiftNight(appOvertime.get().getOverTimeShiftNight());
						result.setFlexExessTime(appOvertime.get().getFlexExessTime());
						result.setApplication(application.get());
						result.setAppID(appOvertime.get().getAppID());
					}
				}
			}
		}
		return result;
	}
}
