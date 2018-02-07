package nts.uk.ctx.at.request.dom.application.holidayworktime.service;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.RecordWorkInfoImport;
import nts.uk.ctx.at.request.dom.application.holidayworktime.HolidayWorkInput;
import nts.uk.ctx.at.request.dom.application.overtime.OvertimeCheckResult;
import nts.uk.ctx.at.request.dom.application.overtime.service.CaculationTime;
import nts.uk.ctx.at.request.dom.setting.workplace.ApprovalFunctionSetting;

public interface HolidayThreeProcess {
	/**
	 * 03-01_事前申請超過チェック
	 * @param companyId: 会社ID
	 * @param appDate: 申請日
	 * @param inputDate: 入力日
	 * @param prePostAtr: 事前事後区分
	 * @param attendanceId: 勤怠種類
	 * @param overtimeInputs: 申請時間(input time in a ATTENDANCE)
	 * @return 0: Normal. 1: 背景色を設定する
	 */
	OvertimeCheckResult preApplicationExceededCheck(String companyId, GeneralDate appDate, GeneralDateTime inputDate, PrePostAtr prePostAtr, int attendanceId, List<HolidayWorkInput> overtimeInputs) ;
	/**
	 * 03-02-2_当日以外の場合
	 * @param companyID
	 * @param employeeID
	 * @param appDate
	 * @param approvalFunctionSetting
	 * @param siftCD
	 * @param breakTimes
	 * @param recordWorkInfoImport
	 * @return
	 */
	public List<CaculationTime> checkOutSideTimeTheDay(String companyID,String employeeID,String appDate,ApprovalFunctionSetting approvalFunctionSetting, String siftCD,List<CaculationTime> breakTimes,RecordWorkInfoImport recordWorkInfoImport);
	/**
	 * 03-02-2-1_当日以外_休日出勤の場合
	 * @param companyID
	 * @param employeeID
	 * @param appDate
	 * @param approvalFunctionSetting
	 * @param siftCD
	 * @param breakTimes
	 * @param recordWorkInfoImport
	 * @return
	 */
	public List<CaculationTime> checkOutSideTimeTheDayForHoliday(String companyID,String employeeID,String appDate,ApprovalFunctionSetting approvalFunctionSetting, String siftCD,List<CaculationTime> breakTimes,RecordWorkInfoImport recordWorkInfoImport);
	/**
	 * checkHolidayWorkOnDay
	 * @param companyID
	 * @param employeeID
	 * @param appDate
	 * @param approvalFunctionSetting
	 * @param siftCD
	 * @param breakTimes
	 * @param recordWorkInfoImport
	 * @return
	 */
	public List<CaculationTime> checkHolidayWorkOnDay(String companyID,String employeeID,String appDate,ApprovalFunctionSetting approvalFunctionSetting, String siftCD,List<CaculationTime> breakTimes,RecordWorkInfoImport recordWorkInfoImport);
}
