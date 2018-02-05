package nts.uk.ctx.at.request.dom.application.holidayworktime.service;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.logging.log4j.util.Strings;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.InstructionCategory;
import nts.uk.ctx.at.request.dom.application.UseAtr;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.EmployeeRequestAdapter;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.output.AppCommonSettingOutput;
import nts.uk.ctx.at.request.dom.application.holidayinstruction.HolidayInstruct;
import nts.uk.ctx.at.request.dom.application.holidayinstruction.HolidayInstructRepository;
import nts.uk.ctx.at.request.dom.application.holidayworktime.service.dto.HolidayWorkInstruction;
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
	
}
