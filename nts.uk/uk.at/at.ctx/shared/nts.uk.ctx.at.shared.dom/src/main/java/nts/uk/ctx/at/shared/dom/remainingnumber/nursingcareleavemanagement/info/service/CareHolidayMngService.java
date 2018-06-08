package nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.service;

import java.util.List;

import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.children.service.ChildCareNursingRemainOutputPara;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

public interface CareHolidayMngService {
	/**
	 * RequestList343: 期間内の介護残を集計する
	 * @param cid
	 * @param sid
	 * @param dateData
	 * @param mode True: 月次モード, false: その他モード
	 * @return
	 */
	public List<ChildCareNursingRemainOutputPara> calCareRemainOfInPerior(String cid, String sid, DatePeriod dateData, boolean mode); 
}
