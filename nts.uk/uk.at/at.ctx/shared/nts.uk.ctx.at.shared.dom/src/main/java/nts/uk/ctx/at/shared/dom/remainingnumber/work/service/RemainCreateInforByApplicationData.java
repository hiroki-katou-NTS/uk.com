package nts.uk.ctx.at.shared.dom.remainingnumber.work.service;

import java.util.List;

import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.AppRemainCreateInfor;

public interface RemainCreateInforByApplicationData {
	/**
	 * 残数作成元の申請を取得する
	 * @param cid
	 * @param sid
	 * @param dateData 期間
	 * @return
	 */
	List<AppRemainCreateInfor> lstRemainDataFromApp(CacheCarrier cacheCarrier, String cid, String sid, DatePeriod dateData);
	
	/**
	 * 残数作成元の申請を取得する
	 * @param cid
	 * @param sid
	 * @param dateData 期間
	 * @return
	 */
	List<AppRemainCreateInfor> lstRemainDataFromApp(CacheCarrier cacheCarrier, String cid, String sid, GeneralDate baseDate);
	
	/**
	 * 残数作成元の申請を取得する
	 * @param cid
	 * @param sid
	 * @param dateData　リスト
	 * @return
	 */
	List<AppRemainCreateInfor> lstRemainDataFromApp(CacheCarrier cacheCarrier, String cid, String sid, List<GeneralDate> dates);
	/**
	 * 休日を除外する 1: 除く, 0: 除かない
	 * @param appID
	 * @return
	 */
	//Integer excludeHolidayAtr(CacheCarrier cacheCarrier, String cid,String appID);
}
