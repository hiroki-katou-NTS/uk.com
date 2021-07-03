package nts.uk.ctx.at.shared.dom.remainingnumber.work.service;

import java.util.List;

import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.ScheRemainCreateInfor;

public interface RemainCreateInforByScheData {
	/**
	 * 残数作成元の勤務予定を取得する
	 * @param cid
	 * @param sid
	 * @param dateData 期間
	 * @return
	 */
	List<ScheRemainCreateInfor> createRemainInfor(CacheCarrier cacheCarrier, String cid, String sid, DatePeriod dateData);
	
	/**
	 * 暫定データを作成する為の勤務予定を取得する
	 * @param cid
	 * @param sid
	 * @param ymd 年月日
	 * @return
	 */
	List<ScheRemainCreateInfor> createRemainInforNew(String sid, List<GeneralDate> dates);
}
