package nts.uk.ctx.at.request.dom.application.overtime.service;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.UseAtr;
import nts.uk.ctx.at.request.dom.application.common.adapter.frame.OvertimeInputCaculation;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.before.IErrorCheckBeforeRegister;
import nts.uk.ctx.at.request.dom.application.overtime.AppOverTime;
import nts.uk.ctx.at.request.dom.application.overtime.AttendanceID;
import nts.uk.ctx.at.request.dom.application.overtime.OverTimeInput;
import nts.uk.ctx.at.request.dom.application.overtime.OvertimeCheckResult;
import nts.uk.ctx.at.request.dom.application.overtime.OvertimeInputRepository;
import nts.uk.ctx.at.request.dom.application.overtime.OvertimeRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon.OvertimeRestAppCommonSetRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon.OvertimeRestAppCommonSetting;
import nts.uk.ctx.at.request.dom.setting.requestofeach.RequestAppDetailSetting;

public class OvertimeSixProcessImpl implements OvertimeSixProcess{
	final String DATE_FORMAT = "yyyy/MM/dd";
	@Inject
	private IErrorCheckBeforeRegister IErrorCheckBeforeRegister;
	@Inject
	private OvertimeRestAppCommonSetRepository overtimeRestAppCommonSetRepository;
	@Inject
	private OvertimeService overtimeService;
	@Inject
	private ApplicationRepository applicationRepository;
	@Inject
	private OvertimeRepository overtimeRepository;
	@Inject
	private OvertimeInputRepository overtimeInputRepository;
	
	@Override
	public void checkDisplayColor(List<OverTimeInput> overTimeInputs,
			List<OvertimeInputCaculation> overtimeInputCaculations,int prePostAtr,GeneralDateTime inputDate,GeneralDate appDate, int appType,String employeeID,String companyID,RequestAppDetailSetting requestAppDetailSetting) {
		
		for(OverTimeInput overtimeInput : overTimeInputs ){
			for(OvertimeInputCaculation overtimeInputCaculation : overtimeInputCaculations){
					if(overtimeInput.getFrameNo() == overtimeInputCaculation.getFrameNo()){
						if(overtimeInput.getStartTime().v() == overtimeInputCaculation.getResultCaculation()){
							if(overtimeInput.getStartTime().v() == 0){
								continue;
							}else if(overtimeInput.getStartTime().v() > 0){
								// 03-01_事前申請超過チェック
								OvertimeCheckResult overtimeCheckResult = this.IErrorCheckBeforeRegister.preApplicationExceededCheck(overtimeInput.getCompanyID(),appDate, inputDate, EnumAdaptor.valueOf(prePostAtr, PrePostAtr.class), overtimeInputCaculation.getAttendanceID(), overTimeInputs);
								// 06-04_計算実績超過チェック
								checkCaculationActualExcess(prePostAtr,appType,employeeID,companyID,requestAppDetailSetting);
							}
						}else{
							// in màu
						}
					}
			}
		}
		
	}
	
	@Override
	public void checkCaculationActualExcess(int prePostAtr,int appType,String employeeID,String companyID,RequestAppDetailSetting requestAppDetailSetting) {
		boolean codition = checkCondition(prePostAtr,appType,companyID);
		if(codition){
			// 08_就業時間帯取得
			SiftType siftType = overtimeService.getSiftType(companyID, employeeID, requestAppDetailSetting);
			// TO DO...
		}
		
	}
	@Override
	public boolean checkCondition(int prePostAtr,int appType,String companyID) {
		if(prePostAtr == PrePostAtr.POSTERIOR.value){
			Optional<OvertimeRestAppCommonSetting> overtimeRestAppCommonSetting = overtimeRestAppCommonSetRepository.getOvertimeRestAppCommonSetting(companyID, appType);
			if(overtimeRestAppCommonSetting.isPresent()){
				//ドメインモデル「残業休出申請共通設定」.実績表示区分チェック
				if(overtimeRestAppCommonSetting.get().getPerformanceDisplayAtr().value == UseAtr.USE.value){
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public AppOverTime getAppOvertimePre(String companyID,String employeeId, String appDate,int appType) {
		AppOverTime result = new AppOverTime();
		Application applicationOvertime = new Application();
		Optional<OvertimeRestAppCommonSetting> overtimeRestAppCommonSetting = overtimeRestAppCommonSetRepository.getOvertimeRestAppCommonSetting(companyID, appType);
		if(overtimeRestAppCommonSetting.isPresent()){
			if(overtimeRestAppCommonSetting.get().getPreDisplayAtr().value == UseAtr.USE.value){
				List<Application> application = this.applicationRepository.getApp(employeeId,  GeneralDate.fromString(appDate, DATE_FORMAT), PrePostAtr.PREDICT.value, appType);
				if(application.size() > 0){
					applicationOvertime.setApplicationDate(application.get(0).getApplicationDate());
					Optional<AppOverTime> appOvertime = this.overtimeRepository.getAppOvertime(application.get(0).getCompanyID(), application.get(0).getApplicationID());
					if(appOvertime.isPresent()){
						result.setWorkTypeCode(appOvertime.get().getWorkTypeCode());
						result.setSiftCode(appOvertime.get().getSiftCode());
						result.setWorkClockFrom1(appOvertime.get().getWorkClockFrom1());
						result.setWorkClockTo1(appOvertime.get().getWorkClockTo1());
						result.setWorkClockFrom2(appOvertime.get().getWorkClockFrom2());
						result.setWorkClockTo2(appOvertime.get().getWorkClockTo2());
						
						List<OverTimeInput> overtimeInputs = overtimeInputRepository.getOvertimeInputByAttendanceId(appOvertime.get().getCompanyID(), appOvertime.get().getAppID(),AttendanceID.NORMALOVERTIME.value);
						result.setOverTimeInput(overtimeInputs);
						result.setOverTimeShiftNight(appOvertime.get().getOverTimeShiftNight());
						result.setFlexExessTime(appOvertime.get().getFlexExessTime());
						result.setApplication(applicationOvertime);
						result.setAppID(appOvertime.get().getAppID());
						return result;
					}
				}
			}
		}
		return null;
	}

}
