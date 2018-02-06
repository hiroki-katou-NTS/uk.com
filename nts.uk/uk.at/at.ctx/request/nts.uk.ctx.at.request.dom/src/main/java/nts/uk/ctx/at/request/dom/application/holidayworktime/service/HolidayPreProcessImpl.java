package nts.uk.ctx.at.request.dom.application.holidayworktime.service;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.logging.log4j.util.Strings;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.application.AppReason;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository_New;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.InstructionCategory;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.UseAtr;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.EmployeeRequestAdapter;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.output.AppCommonSettingOutput;
import nts.uk.ctx.at.request.dom.application.holidayinstruction.HolidayInstruct;
import nts.uk.ctx.at.request.dom.application.holidayinstruction.HolidayInstructRepository;
import nts.uk.ctx.at.request.dom.application.holidayworktime.AppHolidayWork;
import nts.uk.ctx.at.request.dom.application.holidayworktime.AppHolidayWorkRepository;
import nts.uk.ctx.at.request.dom.application.holidayworktime.HolidayWorkInput;
import nts.uk.ctx.at.request.dom.application.holidayworktime.HolidayWorkInputRepository;
import nts.uk.ctx.at.request.dom.application.holidayworktime.service.dto.HolidayWorkInstruction;
import nts.uk.ctx.at.request.dom.application.overtime.AttendanceType;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon.OvertimeRestAppCommonSetting;
import nts.uk.shr.com.time.TimeWithDayAttr;
@Stateless
public class HolidayPreProcessImpl implements HolidayPreProcess {
	final static String DATE_FORMAT = "yyyy/MM/dd";
	final static String ZEZO_TIME = "00:00";
	final static String DATE_TIME_FORMAT = "yyyy/MM/dd HH:mm";
	final static String SPACE = " ";
	@Inject
	private HolidayInstructRepository holidayInstructRepository;
	@Inject
	private EmployeeRequestAdapter employeeAdapter;
	@Inject
	private ApplicationRepository_New applicationRepository;
	@Inject
	private AppHolidayWorkRepository appHolidayWorkRepository;
	@Inject
	private HolidayWorkInputRepository holidayWorkInputRepository;

	@Override
	public HolidayWorkInstruction getHolidayInstructionInformation(AppCommonSettingOutput appCommonSettingOutput,
			String appDate, String employeeID) {
		HolidayWorkInstruction holidayInstructInformation = new HolidayWorkInstruction();
		if (appCommonSettingOutput != null) {
			if(appCommonSettingOutput.approvalFunctionSetting != null){
				int useAtr = appCommonSettingOutput.approvalFunctionSetting.getInstructionUseSetting().getInstructionAtr().value;
				if (useAtr == UseAtr.USE.value && appCommonSettingOutput.approvalFunctionSetting.getInstructionUseSetting().getInstructionAtr().value == InstructionCategory.HOLIDAYWORK.value) {
					if (appDate != null) {
						holidayInstructInformation.setDisplayHolidayWorkInstructInforFlg(true);
						HolidayInstruct overtimeInstruct = holidayInstructRepository
								.getHolidayWorkInstruct(GeneralDate.fromString(appDate, DATE_FORMAT), employeeID);
						if (overtimeInstruct != null) {
							TimeWithDayAttr startTime = new TimeWithDayAttr(
									overtimeInstruct.getStartClock() == null ? -1 : overtimeInstruct.getStartClock().v());
							TimeWithDayAttr endTime = new TimeWithDayAttr(
									overtimeInstruct.getEndClock() == null ? -1 : overtimeInstruct.getEndClock().v());
							holidayInstructInformation
									.setHolidayWorkInstructInfomation(overtimeInstruct.getInstructDate().toString() + " "
											+ startTime.getDayDivision().description + " "
											+ convert(overtimeInstruct.getStartClock().v()) + "~"
											+ endTime.getDayDivision().description + " "
											+ convert(overtimeInstruct.getEndClock().v()) + " "
											+ employeeAdapter.getEmployeeName(overtimeInstruct.getTargetPerson()) + " ("
											+ employeeAdapter.getEmployeeName(overtimeInstruct.getInstructor()) + ")");
						} else {
							holidayInstructInformation.setHolidayWorkInstructInfomation(
									GeneralDate.fromString(appDate, DATE_FORMAT) + "の残業指示はありません。");
						}
					}
				} else {
					holidayInstructInformation.setDisplayHolidayWorkInstructInforFlg(false);
				}
			}
		}
		
		return holidayInstructInformation;
	}
	private String convert(int minute) {
		String hourminute = Strings.EMPTY;
		if (minute == -1) {
			return null;
		} else if (minute == 0) {
			hourminute = ZEZO_TIME;
		} else {
			int hour = minute / 60;
			int hourInDay = hour % 24;
			int minutes = minute % 60;
			hourminute = (hourInDay < 10 ? ("0" + hourInDay) : hourInDay) + ":" + (minutes < 10 ? ("0" + minutes) : minutes);
		}
		return hourminute;
	}
	@Override
	public AppHolidayWork getPreApplicationHoliday(String companyID, String employeeId,
			Optional<OvertimeRestAppCommonSetting> overtimeRestAppCommonSet, String appDate, int prePostAtr) {
			AppHolidayWork result = new AppHolidayWork();
			if(overtimeRestAppCommonSet.isPresent() && overtimeRestAppCommonSet.get().getPreDisplayAtr().value == UseAtr.USE.value){
				List<Application_New> application = this.applicationRepository.getApp(employeeId,
						appDate == null ? null : GeneralDate.fromString(appDate, DATE_FORMAT), PrePostAtr.PREDICT.value,
						ApplicationType.BREAK_TIME_APPLICATION.value);
				if(CollectionUtil.isEmpty(application)){
					Application_New applicationOvertime = Application_New.firstCreate(companyID, EnumAdaptor.valueOf(prePostAtr, PrePostAtr.class), 
							appDate == null ? null :GeneralDate.fromString(appDate, DATE_FORMAT), ApplicationType.OVER_TIME_APPLICATION, employeeId, new AppReason(Strings.EMPTY));
					applicationOvertime.setAppDate(application.get(0).getAppDate());
					Optional<AppHolidayWork> appHolidayWork = this.appHolidayWorkRepository
							.getAppHolidayWork(application.get(0).getCompanyID(), application.get(0).getAppID());
					if (appHolidayWork.isPresent()) {
						result.setWorkTypeCode(appHolidayWork.get().getWorkTypeCode());
						result.setWorkTimeCode(appHolidayWork.get().getWorkTimeCode());
						result.setWorkClock1(appHolidayWork.get().getWorkClock1());
						result.setWorkClock2(appHolidayWork.get().getWorkClock2());
						
						List<HolidayWorkInput> holidayWorkInputs= holidayWorkInputRepository.getHolidayWorkInputByAttendanceType
								(
								appHolidayWork.get().getCompanyID(), appHolidayWork.get().getAppID(),
								AttendanceType.BREAKTIME.value);
						result.setHolidayWorkInputs(holidayWorkInputs);
						result.setApplication(applicationOvertime);
						result.setAppID(appHolidayWork.get().getAppID());
						return result;
				}
			}
			
		}
		return null;
	}
}
