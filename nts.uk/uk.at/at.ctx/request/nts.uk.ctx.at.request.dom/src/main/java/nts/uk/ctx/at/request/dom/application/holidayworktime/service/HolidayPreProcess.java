package nts.uk.ctx.at.request.dom.application.holidayworktime.service;

import java.util.Map;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.UseAtr;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.dailyattendancetime.TimeWithCalculationImport;
import nts.uk.ctx.at.request.dom.application.holidayworktime.service.dto.HolidayWorkInstruction;
import nts.uk.ctx.at.request.dom.application.overtime.service.CaculationTime;

public interface HolidayPreProcess {
	/**
	 * 01-01_休出通知情報を取得
	 * @param instructionUseDivision 休出指示の利用区分
	 * @param date 申請日
	 * @param employeeID 申請者
	 * @return
	 */
	public HolidayWorkInstruction getHolidayInstructionInformation(UseAtr instructionUseDivision, GeneralDate date, String employeeID);
	/**
	 * 01-10_0時跨ぎチェック
	 * @param holidayWorkCal
	 * @return
	 */
	public CaculationTime  getOverTimeHourCal(Map<Integer,TimeWithCalculationImport> holidayWorkCal);
}
