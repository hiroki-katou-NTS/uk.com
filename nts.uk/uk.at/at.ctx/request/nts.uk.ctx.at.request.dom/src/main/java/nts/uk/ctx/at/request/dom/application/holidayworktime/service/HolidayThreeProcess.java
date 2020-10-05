package nts.uk.ctx.at.request.dom.application.holidayworktime.service;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.RecordWorkInfoImport_Old;
import nts.uk.ctx.at.request.dom.application.overtime.service.CaculationTime;

public interface HolidayThreeProcess {
	
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
			String siftCD,Integer calTime, boolean isCalculator, boolean actualExceedConfirm);
	
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
	public CaculationTime checkOutSideTimeTheDay(String companyID,String employeeID,String appDate, String siftCD,CaculationTime breakTime,RecordWorkInfoImport_Old recordWorkInfoImport,Integer calTime,String employeeName);
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
	public CaculationTime checkOutSideTimeTheDayForHoliday(String companyID,String employeeID,String appDate, String siftCD,CaculationTime breakTime,RecordWorkInfoImport_Old recordWorkInfoImport,Integer calTime,String employeeName);
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
	public CaculationTime checkOutSideTimeTheDayNoForHoliday(String companyID,String employeeID,String appDate, String siftCD,CaculationTime breakTime,RecordWorkInfoImport_Old recordWorkInfoImport,Integer calTime,String employeeName);
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
	 * @param actualExceedConfirm
	 * @return
	 */
	public CaculationTime checkHolidayWorkOnDay(String companyID,String employeeID,String appDate, String siftCD,CaculationTime breakTimes,RecordWorkInfoImport_Old recordWorkInfoImport,Integer calTime,String employeeName, boolean actualExceedConfirm);
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
	public void checkOnDayTheDayForHolidayWork(String companyID,String employeeID,String appDate, String siftCD,CaculationTime breakTimes,RecordWorkInfoImport_Old recordWorkInfoImport,Integer calTime,String employeeName, boolean actualExceedConfirm);
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
	public void checkDayIsHoliday(String companyID,String employeeID,String appDate, String siftCD,CaculationTime breakTimes,RecordWorkInfoImport_Old recordWorkInfoImport,Integer calTime,String employeeName);
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
