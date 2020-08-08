package nts.uk.ctx.at.request.dom.application.holidayworktime.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.dom.application.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.UseAtr;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.dailyattendancetime.TimeWithCalculationImport;
import nts.uk.ctx.at.request.dom.application.holidayworktime.AppHolidayWorkRepository;
import nts.uk.ctx.at.request.dom.application.holidayworktime.HolidayWorkInput;
import nts.uk.ctx.at.request.dom.application.holidayworktime.HolidayWorkInputRepository;
import nts.uk.ctx.at.request.dom.application.overtime.service.CaculationTime;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon.OvertimeRestAppCommonSetRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon.OvertimeRestAppCommonSetting;

@Stateless
public class HolidaySixProcessImpl implements HolidaySixProcess{
	private static final String DATE_FORMAT = "yyyy/MM/dd";
	@Inject
	private OvertimeRestAppCommonSetRepository overtimeRestAppCommonSetRepository;
	@Inject
	private ApplicationRepository applicationRepository;
	@Inject
	private AppHolidayWorkRepository appHolidayWorkRepository;
	@Inject
	private HolidayWorkInputRepository holidayWorkInputRepository;
	
	public List<HolidayWorkInput> convert(CaculationTime caculationTime){
	List<HolidayWorkInput> holidayInputs = new ArrayList<>();
		if(caculationTime.getApplicationTime() != null){
			HolidayWorkInput holidayInput = HolidayWorkInput.createSimpleFromJavaType(caculationTime.getCompanyID(),
					caculationTime.getAppID(),
					caculationTime.getAttendanceID(), 
					caculationTime.getFrameNo(),
					-1,
					-1,
					caculationTime.getApplicationTime());
			holidayInputs.add(holidayInput);
		}
	
	return holidayInputs;
}
	@Override
	public List<CaculationTime> getCaculationHolidayWork(String companyID, String employeeId, String appDate,
			int appType, List<CaculationTime> holidayWorks, Map<Integer, TimeWithCalculationImport> holidayWorkCal,int prePostAtr) {
		// 0時跨ぎチェック
		//事前申請を取得
		if(prePostAtr == 1){
			Optional<OvertimeRestAppCommonSetting> overtimeRestAppCommonSetting = overtimeRestAppCommonSetRepository.getOvertimeRestAppCommonSetting(companyID, appType);
			if(overtimeRestAppCommonSetting.isPresent()){
				if(overtimeRestAppCommonSetting.get().getPreDisplayAtr().value == UseAtr.USE.value){
//					List<Application_New> application = this.applicationRepository.getApp(employeeId,  GeneralDate.fromString(appDate, DATE_FORMAT), PrePostAtr.PREDICT.value, appType);
//					if(application.size() > 0){
//						Optional<AppHolidayWork> appHolidayWork = this.appHolidayWorkRepository
//								.getAppHolidayWork(application.get(0).getCompanyID(), application.get(0).getAppID());
//						if(appHolidayWork.isPresent()){
//							List<HolidayWorkInput> holidayWorkInputs = holidayWorkInputRepository.getHolidayWorkInputByAttendanceType(appHolidayWork.get().getCompanyID(), appHolidayWork.get().getAppID(),
//									AttendanceType.BREAKTIME.value);
//							for(HolidayWorkInput holidayWorkInput : holidayWorkInputs){
//								for(CaculationTime cal : holidayWorks){
//									if(cal.getFrameNo() == holidayWorkInput.getFrameNo()){
//										cal.setPreAppTime(Integer.toString(holidayWorkInput.getApplicationTime().v()));
//									}
//								}
//							}
//						}
//					}
				}
			}
		}
		
		// 06-02-2_申請時間を取得
		for(CaculationTime cal : holidayWorks){
			for(Map.Entry<Integer, TimeWithCalculationImport> entry : holidayWorkCal.entrySet()){
				if(cal.getFrameNo() == entry.getKey()){
					cal.setApplicationTime(entry.getValue().getCalTime());
				}
			}
		}
		return holidayWorks;
	}

}
