package nts.uk.ctx.at.request.dom.application.holidayworktime.service;

import java.util.List;
import java.util.Map;

import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.dailyattendancetime.TimeWithCalculationImport;
import nts.uk.ctx.at.request.dom.application.overtime.service.CaculationTime;

public interface HolidaySixProcess {
	
	/**
	 * 06-01_色表示チェック
	 * @param breakTimeInputs
	 * @param overtimeInputCaculations
	 * @param prePostAtr
	 * @param inputDate
	 * @param appDate
	 * @param appType
	 * @param employeeID
	 * @param companyID
	 * @param approvalFunctionSetting
	 * @param siftCD
	 * @return
	 */
	public List<CaculationTime> checkDisplayColor(List<CaculationTime> breakTimeInputs,
			Map<Integer,TimeWithCalculationImport> holidayWorkCal,int prePostAtr,GeneralDateTime inputDate,GeneralDate appDate,int appType,String employeeID,String companyID ,String siftCD);

	/**
	 *  06-02_休出時間を取得
	 * @param companyID
	 * @param employeeId
	 * @param appDate
	 * @param appType
	 * @param holidayWorks
	 * @param holidayWorkCal
	 * @return
	 */
	public List<CaculationTime> getCaculationHolidayWork(String companyID,String employeeId, String appDate,int appType,List<CaculationTime> holidayWorks,Map<Integer,TimeWithCalculationImport> holidayWorkCal,int prePostAtr);
	/**
	 * 06-01-a_色表示チェック（承認者）
	 * @param breakTimeInputs
	 * @param holidayWorkCal
	 * @param prePostAtr
	 * @param inputDate
	 * @param appDate
	 * @param appType
	 * @param employeeID
	 * @param companyID
	 * @param siftCD
	 * @return
	 */
	public List<CaculationTime> checkDisplayColorForApprover(List<CaculationTime> breakTimeInputs,
			Map<Integer,TimeWithCalculationImport> holidayWorkCal,int prePostAtr,GeneralDateTime inputDate,GeneralDate appDate,int appType,String employeeID,String companyID ,String siftCD);
}
