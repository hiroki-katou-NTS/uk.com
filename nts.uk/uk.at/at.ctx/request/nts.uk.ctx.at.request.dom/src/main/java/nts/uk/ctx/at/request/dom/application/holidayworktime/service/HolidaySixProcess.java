package nts.uk.ctx.at.request.dom.application.holidayworktime.service;

import java.util.List;
import java.util.Map;

import nts.uk.ctx.at.request.dom.application.common.adapter.record.dailyattendancetime.TimeWithCalculationImport;
import nts.uk.ctx.at.request.dom.application.holidayworktime.HolidayWorkInput;
import nts.uk.ctx.at.request.dom.application.overtime.service.CaculationTime;

public interface HolidaySixProcess {

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
	
	public List<HolidayWorkInput> convert(CaculationTime caculationTime);
}
