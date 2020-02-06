package nts.uk.ctx.at.shared.dom.remainingnumber.work.service;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.ScheRemainCreateInfor;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

public interface RemainCreateInforByScheData {
	/**
	 * 残数作成元の勤務予定を取得する
	 * @param cid
	 * @param sid
	 * @param dateData 期間
	 * @return
	 */
	List<ScheRemainCreateInfor> createRemainInfor(String cid, String sid, DatePeriod dateData);
	/**
	 * 
	 * @param cid
	 * @param sid
	 * @param dates　リスト
	 * @return
	 */
	List<ScheRemainCreateInfor> createRemainInfor(String cid, String sid, List<GeneralDate> dates);
}
