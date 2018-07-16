package nts.uk.ctx.at.shared.dom.remainingnumber.work.service;

import java.util.List;

import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.ScheRemainCreateInfor;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

public interface RemainCreateInforByScheData {
	/**
	 * 残数作成元の勤務予定を取得する
	 * @param cid
	 * @param sid
	 * @param dateData
	 * @return
	 */
	List<ScheRemainCreateInfor> createRemainInfor(String cid, String sid, DatePeriod dateData);
}
