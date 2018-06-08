package nts.uk.ctx.at.function.dom.adapter.nursingcareleavemanagement;

import java.util.List;

import nts.uk.shr.com.time.calendar.period.DatePeriod;



public interface CareHolidayMngAdapter {
	
	/**
	 * RequestList343: 期間内の介護残を集計する
	 * @param cid
	 * @param sid
	 * @param dateData
	 * @param mode True: 月次モード, false: その他モード
	 * @return
	 */
	public NursingCareLeaveImported calCareRemainOfInPerior(String cid, String sid, DatePeriod dateData, boolean mode); 

}
