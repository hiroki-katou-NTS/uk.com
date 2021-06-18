package nts.uk.ctx.at.shared.dom.remainingnumber.work.service;

import java.util.List;

import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.DailyResult;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.RecordRemainCreateInfor;

public interface RemainCreateInforByRecordData {
	/**
	 * 残数作成元の勤務実績を取得する
	 * @param cid
	 * @param sid
	 * @param dateData 期間
	 * @return
	 */
	public List<RecordRemainCreateInfor> lstRecordRemainData(CacheCarrier cacheCarrier, String cid, String sid, DatePeriod dateData);
	/**
	 * 残数作成元の勤務実績を取得する
	 * @param cid
	 * @param sid
	 * @param dateData リスト
	 * @return
	 */
	public List<RecordRemainCreateInfor> lstRecordRemainData(CacheCarrier cacheCarrier, String cid, String sid, List<GeneralDate> dateData);

	/**
	 * 暫定データを作成する為の日別実績を取得する(DBから日別実績を取得)
	 * @param cid
	 * @param sid
	 * @param dateData リスト
	 * @return
	 */
	public List<RecordRemainCreateInfor> lstRecordRemainData(String sid, List<GeneralDate> dateData);

	/**
	 * 暫定データを作成する為の日別実績を取得する(パラメータから日別実績を取得)
	 * @param sid
	 * @param dailyResults
	 * @return
	 */
	public List<RecordRemainCreateInfor> lstResultFromRecord(String sid, List<DailyResult> dailyResults);

}
