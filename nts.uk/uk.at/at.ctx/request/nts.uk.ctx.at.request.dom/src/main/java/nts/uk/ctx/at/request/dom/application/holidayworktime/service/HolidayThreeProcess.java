package nts.uk.ctx.at.request.dom.application.holidayworktime.service;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.RecordWorkInfoImport;
import nts.uk.ctx.at.request.dom.application.holidayworktime.HolidayWorkInput;
import nts.uk.ctx.at.request.dom.application.overtime.OvertimeCheckResult;
import nts.uk.ctx.at.request.dom.application.overtime.service.CaculationTime;

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
	OvertimeCheckResult preApplicationExceededCheck(String companyId, GeneralDate appDate, GeneralDateTime inputDate, PrePostAtr prePostAtr, 
			int attendanceId, List<HolidayWorkInput> overtimeInputs, String employeeID) ;
	
	/**
	 * 03-02_実績超過チェック
	 * @param prePostAtr
	 * @param appType
	 * @param employeeID
	 * @param companyID
	 * @param approvalFunctionSetting
	 * @param appDate
	 * @param overTimeInputs
	 * @param siftCD
	 * @return
	 */
	public CaculationTime checkCaculationActualExcess(int prePostAtr,int appType,String employeeID,String companyID,GeneralDate appDate,CaculationTime breakTimeInput, 
			String siftCD,Integer calTime, boolean isCalculator);
	/**
	 * 03-02-1_チェック条件
	 * @param prePostAtr
	 * @param companyID
	 * @return
	 */
	public boolean checkCodition(int prePostAtr,String companyID, boolean isCalculator);
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
	public CaculationTime checkOutSideTimeTheDay(String companyID,String employeeID,String appDate, String siftCD,CaculationTime breakTime,RecordWorkInfoImport recordWorkInfoImport,Integer calTime,String employeeName);
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
	public CaculationTime checkOutSideTimeTheDayForHoliday(String companyID,String employeeID,String appDate, String siftCD,CaculationTime breakTime,RecordWorkInfoImport recordWorkInfoImport,Integer calTime,String employeeName);
	/**
	 * 03-02-2-2_当日以外_休日の場合
	 * @param companyID
	 * @param employeeID
	 * @param appDate
	 * @param siftCD
	 * @param breakTime
	 * @param recordWorkInfoImport
	 * @param calTime
	 * @return
	 */
	public CaculationTime checkOutSideTimeTheDayNoForHoliday(String companyID,String employeeID,String appDate, String siftCD,CaculationTime breakTime,RecordWorkInfoImport recordWorkInfoImport,Integer calTime,String employeeName);
	/**
	 * 03-02-3_当日の場合
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
	public CaculationTime checkHolidayWorkOnDay(String companyID,String employeeID,String appDate, String siftCD,CaculationTime breakTimes,RecordWorkInfoImport recordWorkInfoImport,Integer calTime,String employeeName);
	/**
	 * 03-02-3-1_当日_休日出勤の場合
	 * @param companyID
	 * @param employeeID
	 * @param appDate
	 * @param siftCD
	 * @param breakTimes
	 * @param recordWorkInfoImport
	 * @param calTime
	 */
	public void checkOnDayTheDayForHolidayWork(String companyID,String employeeID,String appDate, String siftCD,CaculationTime breakTimes,RecordWorkInfoImport recordWorkInfoImport,Integer calTime,String employeeName);
	/**
	 * 03-02-3-2_当日_休日の場合
	 * @param companyID
	 * @param employeeID
	 * @param appDate
	 * @param siftCD
	 * @param breakTimes
	 * @param recordWorkInfoImport
	 * @param calTime
	 */
	public void checkDayIsHoliday(String companyID,String employeeID,String appDate, String siftCD,CaculationTime breakTimes,RecordWorkInfoImport recordWorkInfoImport,Integer calTime,String employeeName);
	/**
	 * 03-02-a_実績超過チェック（承認者）	
	 * @param prePostAtr
	 * @param appType
	 * @param employeeID
	 * @param companyID
	 * @param appDate
	 * @param breakTimeInput
	 * @param siftCD
	 * @param calTime
	 * @return
	 */
	public CaculationTime checkCaculationActualExcessForApprover(int prePostAtr,int appType,String employeeID,String companyID,GeneralDate appDate,
			CaculationTime breakTimeInput, String siftCD,Integer calTime, boolean isCalculator);
}
