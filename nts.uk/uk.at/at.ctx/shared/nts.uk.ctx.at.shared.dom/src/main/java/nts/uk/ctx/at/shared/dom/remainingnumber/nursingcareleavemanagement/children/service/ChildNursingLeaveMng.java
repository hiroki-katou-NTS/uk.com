package nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.children.service;

import java.util.List;

import nts.uk.shr.com.time.calendar.period.DatePeriod;

public interface ChildNursingLeaveMng {
	/**
	 * RequetList341 期間内の子看護残を集計する		
	 * @param cid
	 * @param sid
	 * @param dateData
	 * @param mode True: 月次モード, false: その他モード
	 * @return
	 */
	public ChildCareNursingRemainOutputPara calChildNursOfInPeriod(String cid, String sid, DatePeriod dateData, boolean mode);
}
