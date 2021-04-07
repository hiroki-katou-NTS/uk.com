package nts.uk.ctx.at.request.dom.application.holidayworktime.service;

import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.logging.log4j.util.Strings;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.UseAtr;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.EmployeeRequestAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.dailyattendancetime.TimeWithCalculationImport;
import nts.uk.ctx.at.request.dom.application.holidayinstruction.HolidayInstruct;
import nts.uk.ctx.at.request.dom.application.holidayinstruction.HolidayInstructRepository;
import nts.uk.ctx.at.request.dom.application.holidayworktime.service.dto.HolidayWorkInstruction;
import nts.uk.ctx.at.request.dom.application.overtime.service.CaculationTime;
import nts.uk.shr.com.time.TimeWithDayAttr;
@Stateless
public class HolidayPreProcessImpl implements HolidayPreProcess {
	
	final static String DATE_FORMAT = "yyyy/MM/dd";
	
	final static String ZEZO_TIME = "00:00";
	
	final static String DATE_TIME_FORMAT = "yyyy/MM/dd HH:mm";
	
	final static String SPACE = " ";
	
	final static String MESSAGE = "の休出指示はありません。";
	
	final static String ZEZO = "0";
	
	final static String COLON = ":";
	
	@Inject
	private HolidayInstructRepository holidayInstructRepository;
	
	@Inject
	private EmployeeRequestAdapter employeeAdapter;

	

	@Override
	public HolidayWorkInstruction getHolidayInstructionInformation(UseAtr instructionUseDivision, GeneralDate date, String employeeID) {
		HolidayWorkInstruction holidayInstructInformation = new HolidayWorkInstruction();
		// 休出指示の利用区分チェック
		if(instructionUseDivision != UseAtr.USE) {
			return holidayInstructInformation;
		}
		// 申請日付チェック
		if(date == null) {
			return holidayInstructInformation;
		}
		holidayInstructInformation.setDisplayHolidayWorkInstructInforFlg(true);
		// ドメインモデル「休出指示」を取得
		HolidayInstruct overtimeInstruct = holidayInstructRepository
				.getHolidayWorkInstruct(date, employeeID);
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
			holidayInstructInformation.setHolidayWorkInstructInfomation(date + MESSAGE);
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
			hourminute = (hourInDay < 10 ? (ZEZO + hourInDay) : hourInDay) + COLON + (minutes < 10 ? (ZEZO + minutes) : minutes);
		}
		return hourminute;
	}
	
	@Override
	//01-10_0時跨ぎチェック
	public CaculationTime getOverTimeHourCal(Map<Integer, TimeWithCalculationImport> holidayWorkCal) {
		for(Map.Entry<Integer, TimeWithCalculationImport> entry : holidayWorkCal.entrySet()){
			
			if(entry.getValue().getCalTime() == null || entry.getValue().getCalTime() == 0){
				return null;
			}
		}
		return null;
	}
}
