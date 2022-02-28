package nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.interim;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;

public interface TmpAnnualHolidayMngRepository {

	/**
	 * 削除
	 * @param mngId
	 */
	public void deleteById(String mngId);
	/**
	 * 登録および更新
	 * @param dataMng
	 */
	public void persistAndUpdate(TempAnnualLeaveMngs dataMng);
	/**
	 * 検索　 暫定年休管理データ
	 * @param sid　社員ID
	 * @param period 期間
	 * @return
	 */
	List<TempAnnualLeaveMngs> getBySidPeriod(String sid, DatePeriod period);
	/**
	 * 暫定残数管理データ、暫定年休管理データを削除
	 * @param sid　社員ID
	 * @param period　期間
	 */
	public void deleteSidPeriod(String sid, DatePeriod period);
	
	void deleteSidAndYmd(String sid, GeneralDate ymd);
	
	/**
	 * 年月日より前全て削除
	 * @param sid
	 * @param ymd
	 */
	public void deleteBySidBeforeTheYmd(String sid, GeneralDate ymd);
}
