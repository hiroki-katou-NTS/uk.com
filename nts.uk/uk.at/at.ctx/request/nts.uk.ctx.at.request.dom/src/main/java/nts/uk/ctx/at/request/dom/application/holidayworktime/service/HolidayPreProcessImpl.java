package nts.uk.ctx.at.request.dom.application.holidayworktime.service;

import javax.ejb.Stateless;

import nts.uk.ctx.at.request.dom.application.common.service.newscreen.output.AppCommonSettingOutput;
import nts.uk.ctx.at.request.dom.application.holidayworktime.service.dto.HolidayWorkInstruction;
@Stateless
public class HolidayPreProcessImpl implements HolidayPreProcess {

	@Override
	public HolidayWorkInstruction getHolidayInstructionInformation(AppCommonSettingOutput appCommonSettingOutput,
			String appDate, String employeeID) {
		HolidayWorkInstruction overtimeInstructInformation = new HolidayWorkInstruction();
		if (appCommonSettingOutput != null) {
			if(appCommonSettingOutput.approvalFunctionSetting != null){
//				int useAtr = appCommonSettingOutput.approvalFunctionSetting.getInstructionUseSetting().getInstructionAtr().value;
//				if (useAtr == UseAtr.USE.value) {
//					if (appDate != null) {
//						overtimeInstructInformation.setDisplayHolidayWorkInstructInforFlg(true);
//						OverTimeInstruct overtimeInstruct = overtimeInstructRepository
//								.getOvertimeInstruct(GeneralDate.fromString(appDate, DATE_FORMAT), employeeID);
//						if (overtimeInstruct != null) {
//							TimeWithDayAttr startTime = new TimeWithDayAttr(
//									overtimeInstruct.getStartClock() == null ? -1 : overtimeInstruct.getStartClock().v());
//							TimeWithDayAttr endTime = new TimeWithDayAttr(
//									overtimeInstruct.getEndClock() == null ? -1 : overtimeInstruct.getEndClock().v());
//							overtimeInstructInformation
//									.setOvertimeInstructInfomation(overtimeInstruct.getInstructDate().toString() + " "
//											+ startTime.getDayDivision().description + " "
//											+ convert(overtimeInstruct.getStartClock().v()) + "~"
//											+ endTime.getDayDivision().description + " "
//											+ convert(overtimeInstruct.getEndClock().v()) + " "
//											+ employeeAdapter.getEmployeeName(overtimeInstruct.getTargetPerson()) + " ("
//											+ employeeAdapter.getEmployeeName(overtimeInstruct.getInstructor()) + ")");
//						} else {
//							overtimeInstructInformation.setOvertimeInstructInfomation(
//									GeneralDate.fromString(appDate, DATE_FORMAT) + "の残業指示はありません。");
//						}
//					}
//				} else {
//					overtimeInstructInformation.setDisplayOvertimeInstructInforFlg(false);
//				}
//			}
		}
		}
		return overtimeInstructInformation;
	}
	
}
