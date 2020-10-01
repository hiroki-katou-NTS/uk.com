package nts.uk.ctx.at.request.dom.application.holidayworktime.service;

import java.util.Map;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.UseAtr;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.dailyattendancetime.TimeWithCalculationImport;
import nts.uk.ctx.at.request.dom.application.holidayworktime.service.dto.AppHolidayWorkPreAndReferDto;
import nts.uk.ctx.at.request.dom.application.holidayworktime.service.dto.HolidayWorkInstruction;
import nts.uk.ctx.at.request.dom.application.overtime.service.CaculationTime;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon.OvertimeRestAppCommonSetting;

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
	 * 01-09_事前申請を取得
	 * @param companyID
	 * @param employeeId
	 * @param overtimeRestAppCommonSet
	 * @param appDate
	 * @param prePostAtr
	 * @return
	 */
	public AppHolidayWorkPreAndReferDto getPreApplicationHoliday(String companyID, String employeeId, Optional<OvertimeRestAppCommonSetting> overtimeRestAppCommonSet,String appDate, int prePostAtr);
	/**
	 * 01-10_0時跨ぎチェック
	 * @param holidayWorkCal
	 * @return
	 */
	public CaculationTime  getOverTimeHourCal(Map<Integer,TimeWithCalculationImport> holidayWorkCal);
	/**
	 * 01-18_実績内容を取得（新規）
	 * @param prePostAtr
	 * @param siftCode
	 * @param companyID
	 * @param employeeID
	 * @param appDate
	 * @param approvalFunctionSetting
	 * @param breakTimes
	 * @return
	 */
	//public AppHolidayWorkPreAndReferDto getResultContentActual(int prePostAtr, String siftCode, String companyID, String employeeID, String appDate,ApprovalFunctionSetting approvalFunctionSetting,List<CaculationTime> breakTimes);
}
