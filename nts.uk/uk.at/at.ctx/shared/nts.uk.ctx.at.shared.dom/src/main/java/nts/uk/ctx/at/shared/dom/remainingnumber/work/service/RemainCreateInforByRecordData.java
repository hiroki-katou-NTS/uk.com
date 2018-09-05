package nts.uk.ctx.at.shared.dom.remainingnumber.work.service;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.RecordRemainCreateInfor;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

public interface RemainCreateInforByRecordData {
	/**
	 * 残数作成元の勤務実績を取得する
	 * @param cid
	 * @param sid
	 * @param dateData 期間
	 * @return
	 */
	public List<RecordRemainCreateInfor> lstRecordRemainData(String cid, String sid, DatePeriod dateData);
	/**
	 * 残数作成元の勤務実績を取得する
	 * @param cid
	 * @param sid
	 * @param dateData リスト
	 * @return
	 */
	public List<RecordRemainCreateInfor> lstRecordRemainData(String cid, String sid, List<GeneralDate> dateData);
	
}
